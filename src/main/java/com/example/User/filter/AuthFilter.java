
package com.example.User.filter;

import com.example.User.constant.APIRequestURL;
import com.example.User.constant.AppConstants;
import com.example.User.constant.HeaderConstants;
import com.example.User.helper.UserUtility;
import com.example.User.utils.Logger;
import com.example.User.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.User.maintainancemode.controller.MaintenanceController.API_MAINTENANCE_URI;


@Component
public class AuthFilter implements Filter {

    private final ApplicationAvailability applicationAvailability;
    private final UserUtility authUtility;

    public AuthFilter(ApplicationAvailability applicationAvailability, UserUtility authUtility) {
        this.applicationAvailability = applicationAvailability;
        this.authUtility = authUtility;
    }

    @Value("${checksum}")
    private boolean checkSum;


    /**
     * Sample filter that populates the MDC on every request.
     */

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {

        /*Utils.printLogInDataDog(null, "API Log"
                , AppConstants.LOG_STATUS_TYPE_INFO);*/

        long startTime = Instant.now().getEpochSecond();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (applicationAvailability.getReadinessState().equals(ReadinessState.REFUSING_TRAFFIC) &&
                !(request).getRequestURI().equals(API_MAINTENANCE_URI) && !(request).getRequestURI().equals(APIRequestURL.DIGIPAY_AUTH_DOCKER_API_URL + APIRequestURL.AUTH_MAINTENANCE_MODE_OFF)) {

            this.sendMaintenanceModeResponse(response);

        }

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String requestId = StringUtils.isEmpty(request.getHeader(HeaderConstants
                .REQUEST_ID)) ? Utils.generateUUID()
                : request.getHeader(HeaderConstants.REQUEST_ID);

        Map<String, Object> stringObjectHashMap = new HashMap<>();

        stringObjectHashMap.put("api_endpoint", request.getRequestURI());
        stringObjectHashMap.put("api_method", request.getMethod());

        MDC.put("request_id", requestId);
        MDC.put("api_endpoint", request.getRequestURI());
        MDC.put("api_method", request.getMethod());
        Logger.info("API End point: " + request.getRequestURI() + " for Request Id :" + requestId);
        this.setRequestHeaders(stringObjectHashMap, request);


        try {
            if ((!Objects.isNull(request.getQueryString()) || request.getRequestURI().split(APIRequestURL.DIGIPAY_AUTH_DOCKER_API_URL)[1].split("/").length == 1)
                    && AppConstants.HTTP_GET_METHOD.equalsIgnoreCase(request.getMethod())) {
                stringObjectHashMap.put("payload", StringUtils.isEmpty(request.getQueryString()) ? request.getQueryString() : URLDecoder.decode(request.getQueryString()));
                Logger.info("Payload :"+ stringObjectHashMap.get("payload"));
                HttpServletRequest modifiedRequest = new SomeHttpServletRequest(request);
                if (checkSum && authUtility.verifyCheckSum(stringObjectHashMap.get("payload") == null ? "" : String.valueOf(stringObjectHashMap.get("payload")), request, AppConstants.HTTPMETHODS.GET_ALL, request.getHeader(HeaderConstants.SIGNATURE))) {
                    this.sendErrorResponse(response);
                    return;
                }
                filterChain.doFilter(modifiedRequest, responseWrapper);
            } else if (AppConstants.HTTP_POST_METHOD.equalsIgnoreCase(request.getMethod()) || AppConstants.HTTP_PUT_METHOD.equalsIgnoreCase(request.getMethod())) {
                if (request.getContentType() != null && request.getContentType().equals("application/json")) {
                    ReadableRequestWrapper requestWrapper = new ReadableRequestWrapper(request);
                    stringObjectHashMap.put("payload", requestWrapper.getReader().lines().collect(Collectors.joining()));
                    Logger.info("Payload :"+ stringObjectHashMap.get("payload"));
                    if ((Objects.equals(request.getMethod(), AppConstants.HTTP_POST_METHOD))
                            && checkSum && authUtility.verifyCheckSum(String.valueOf(stringObjectHashMap.get("payload")), request, AppConstants.HTTPMETHODS.POST, request.getHeader(HeaderConstants.SIGNATURE))) {
                        this.sendErrorResponse(response);
                        return;
                    }
                    filterChain.doFilter(requestWrapper, responseWrapper);
                } else {
                    if ((Objects.equals(request.getMethod(), AppConstants.HTTP_POST_METHOD))
                            && checkSum && authUtility.verifyCheckSum(String.valueOf(stringObjectHashMap.get("payload")), request, AppConstants.HTTPMETHODS.POST, request.getHeader(HeaderConstants.SIGNATURE))) {
                        this.sendErrorResponse(response);
                        return;
                    }
                    filterChain.doFilter(servletRequest, responseWrapper);
                }

            } else {
                String[] restOfUrl = request.getRequestURI().split("/");
                if (restOfUrl.length > 5) {
                    stringObjectHashMap.put("payload", restOfUrl[5]);
                    Logger.info("Payload :"+ stringObjectHashMap.get("payload"));
                    if ((Objects.equals(request.getMethod(), AppConstants.HTTP_GET_METHOD) )
                            && checkSum && authUtility.verifyCheckSum(String.valueOf(stringObjectHashMap.get("payload")), request,
                            request.getMethod().equalsIgnoreCase(AppConstants.HTTP_GET_METHOD) ? AppConstants.HTTPMETHODS.GET : AppConstants.HTTPMETHODS.DELETE, request.getHeader(HeaderConstants.SIGNATURE))) {
                        this.sendErrorResponse(response);
                        return;
                    }
                    filterChain.doFilter(request, response);
                } else {
                    stringObjectHashMap.put("payload", request.getQueryString());
                    Logger.info("Payload :"+ stringObjectHashMap.get("payload"));
                    filterChain.doFilter(servletRequest, responseWrapper);
                }
            }

            String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                    response.getCharacterEncoding());

            MDC.put("status_code", String.valueOf(response.getStatus()));

            if (!HttpMethod.GET.toString().equalsIgnoreCase(request.getMethod())) {

                this.sendAPILogToKafka(responseBody, AppConstants.LOG_STATUS_TYPE_INFO
                        , startTime, stringObjectHashMap, requestId);
            }
            Logger.info("Response body for api endpoint"+ request.getRequestURI() + " For request id: "+requestId);
            Logger.info(Utils.convertObjectToJsonString(responseBody,""));
            responseWrapper.copyBodyToResponse();
        } catch (Exception ex) {

            MDC.put("status_code", String.valueOf(response.getStatus()));

            this.sendAPILogToKafka(ExceptionUtils.getStackTrace(ex), AppConstants.LOG_STATUS_TYPE_ERROR
                    , startTime, stringObjectHashMap, requestId);

            responseWrapper.copyBodyToResponse();

            try {
                throw new Exception(ex);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ThreadContext.clearMap();
    }

    private void sendAPILogToKafka(String responseBody, String logStatusType
            , Long startTime, Map<String, Object> stringObjectMap, String requestId) {


        stringObjectMap.put("response", responseBody);
        stringObjectMap.put("log_status_type", logStatusType);
        stringObjectMap.put("duration", Instant.now().getEpochSecond() - startTime);

        authUtility.produceCommonAPILogs(stringObjectMap, requestId);
    }



    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            Logger.error(ExceptionUtils.getStackTrace(e));
            return "";
        }
    }

    @Override
    public void destroy() {
    }

    public static class ReadableRequestWrapper extends HttpServletRequestWrapper {

        private final Charset encoding;
        private final byte[] rawData;

        private ReadableRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);

            String charEncoding = request.getCharacterEncoding();
            this.encoding = StringUtils.isBlank(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);

            InputStream is = request.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            // read bytes from the input stream and store them in the buffer
            while ((len = is.read(buffer)) != -1) {
                // write bytes from the buffer into the output stream
                os.write(buffer, 0, len);
            }
            this.rawData = os.toByteArray();
            System.out.println(request.getMethod() + " Request Parameter" + this
                    .getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

            return new ServletInputStream() {
                public boolean isFinished() {
                    return false;
                }

                public boolean isReady() {
                    return false;
                }

                public void setReadListener(ReadListener readListener) {
                }

                public int read() {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
        }
    }

    static class SomeHttpServletRequest extends HttpServletRequestWrapper {
        HttpServletRequest request;

        SomeHttpServletRequest(final HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public String getQueryString() {
            return request.getQueryString();
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            String queryString = getQueryString();
            return getParamsFromQueryString(queryString);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(getParameterMap().keySet());
        }

        @Override
        public String[] getParameterValues(final String name) {
            return getParameterMap().get(name);
        }

        private Map<String, String[]> getParamsFromQueryString(final String queryString) {
            Map<String, List<String>> collect = new HashMap<>();
            if (!StringUtils.isEmpty(queryString)) {
                String[] params = queryString.split("&");
                collect = Stream.of(params)
                        .map(x -> x.split("="))
                        .collect(Collectors.groupingBy(
                                x -> x[0],
                                Collectors.mapping(
                                        x -> x.length > 1 ? x[1] : null,
                                        Collectors.toList())));
            }
            return collect.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            x -> x.getValue().toArray(new String[0])));
        }
    }

    private void setRequestHeaders(Map<String, Object> stringObjectMap, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        if (Objects.isNull(headerNames)) {
            return;
        }
        Map<String, Object> headers = new HashMap<>();

        try {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.put(headerName, request.getHeader(headerName));

                if (headerName.equalsIgnoreCase("user_id")) {
                    stringObjectMap.put("user_id", headers.get(headerName));
                }

                if (headerName.equalsIgnoreCase("host")) {
                    stringObjectMap.put("hostname", headers.get(headerName));
                }

                if (headerName.equalsIgnoreCase("ip_address")) {
                    stringObjectMap.put("ip_address", headers.get(headerName));
                }
            }
            stringObjectMap.put("headers", new ObjectMapper().valueToTree(headers));
        } catch (Exception ex) {
            System.out.println(" Error : " + ExceptionUtils.getStackTrace(ex));
        }
    }

    public void sendErrorResponse(HttpServletResponse response) {
        Map<String, Object> mapBodyException = new HashMap<>();
        mapBodyException.put("status", "error");
        mapBodyException.put("code", HttpStatus.UNAUTHORIZED.value());
        mapBodyException.put("message", HttpStatus.UNAUTHORIZED.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), mapBodyException);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void sendMaintenanceModeResponse(HttpServletResponse response) {
        Map<String, Object> mapBodyException = new HashMap<>();
        mapBodyException.put("status", "error");
        mapBodyException.put("code", HttpStatus.SERVICE_UNAVAILABLE.value());
        mapBodyException.put("message", HttpStatus.SERVICE_UNAVAILABLE.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), mapBodyException);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
