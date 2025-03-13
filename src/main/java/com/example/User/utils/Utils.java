package com.example.User.utils;

import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.example.User.api.common.commonresponse.ErrorResponse;
import com.example.User.api.common.commonresponse.SuccessResponse;
import com.example.User.constant.HeaderConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

public class Utils {

    public static HashMap<String, String> getHeaders(HttpHeaders headers) {
        HashMap<String, String> map = new HashMap<>();
        List<String> headerOptional;
        String data = null;

        if (headers.containsKey(HeaderConstants.REQUEST_ID)) {
            headerOptional = headers.get(HeaderConstants.REQUEST_ID);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            Logger.info("Request ID : " + data);
            map.put(HeaderConstants.REQUEST_ID, data);
        }

        if (headers.containsKey(HeaderConstants.COMPANY_ID)) {
            headerOptional = headers.get(HeaderConstants.COMPANY_ID);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            Logger.info("Request ID : " + data);
            map.put(HeaderConstants.COMPANY_ID, data);
        }

        if (headers.containsKey(HeaderConstants.TOKEN_USER_ID)) {
            headerOptional = headers.get(HeaderConstants.TOKEN_USER_ID);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            Logger.info("Request ID : " + data);
            map.put(HeaderConstants.TOKEN_USER_ID, data);
        }
        if (headers.containsKey(HeaderConstants.REMOTE_ADDRESS)) {
            headerOptional = headers.get(HeaderConstants.REMOTE_ADDRESS);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            Logger.info("Remote Address : " + data);
            map.put(HeaderConstants.REMOTE_ADDRESS, data);
        }
        if (headers.containsKey(HeaderConstants.LANGUAGE)) {
            headerOptional = headers.get(HeaderConstants.LANGUAGE);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }

            Logger.info("Language : " + data);
            map.put(HeaderConstants.LANGUAGE, data);
        }
        return map;

    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return Long.toHexString(uuid.getMostSignificantBits())
                + Long.toHexString(uuid.getLeastSignificantBits());

    }

    public static JsonNode generateSuccessResponse(Object object) {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccess(1);
        successResponse.setData(object);
        successResponse.setError(new ArrayList<>());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.convertValue(successResponse, JsonNode.class);
    }

    public static JsonNode generateErrorResponse(String message) {
        ErrorResponse successResponse = new ErrorResponse();
        successResponse.setSuccess(0);
        successResponse.setData(new Object());
        ArrayList<String> errors = new ArrayList<>();
        errors.add(message);
        successResponse.setError(errors);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.convertValue(successResponse, JsonNode.class);
    }

    public static String replaceString(String keyword) {
        if (keyword.contains("+")) {
            return keyword.replace("+", "\\+");
        }
        return keyword;
    }

    public static ResponseEntity<JsonNode> getJsonNodeResponseEntity(CommonAPIDataResponse commonAPIDataResponse) {
        Logger.info(Utils.convertObjectToJsonString(commonAPIDataResponse, "response : "));

        if (commonAPIDataResponse.isCheckValidationFailed()) {
            return new ResponseEntity<>(Utils.generateErrorResponse(commonAPIDataResponse.getValidationMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Utils.generateSuccessResponse(commonAPIDataResponse), HttpStatus.OK);
    }


    /* Convert Class or Object Data To Json String */
    public static String convertObjectToJsonString(Object object, String printMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String rules = objectMapper.writeValueAsString(object);
            Logger.info(printMessage + rules);
            return rules;
        } catch (Exception ex) {
            Logger.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }


    public static Query addCriteriaForDateAndSortingAndPaginationAndSearchKeyword(List<Criteria> criteriaArrayList
            , List<Criteria> searchKeywordCriteriaList, Long start_date, Long end_date, Integer skip
            , Integer limit, String sorting) {
        criteriaArrayList.add(Utils.setStartDateAndEndDate(start_date, end_date, "created_date"));

        Criteria criteria = new Criteria();
        criteria.andOperator(criteriaArrayList);
        if (!Objects.isNull(searchKeywordCriteriaList) && !searchKeywordCriteriaList.isEmpty()) {
            criteria.orOperator(searchKeywordCriteriaList);
        }
        Query query = new Query(criteria);
        Utils.setSkipAndLimit(skip, limit, query);
        Utils.sortBy(query, sorting);
        return query;
    }
//}

    public static void setSkipAndLimit(Integer skip, Integer limit, Query query) {
        if (!Objects.isNull(skip) && !Objects.isNull(limit)) {
            query.skip(skip).limit(limit);
        } else if (!Objects.isNull(skip)) {
            query.skip(skip);
        } else if (!Objects.isNull(limit)) {
            query.limit(limit);
        }
    }
    private static Query getDefaultSorting(Query query) {
        query.with(Sort.by(Sort.Direction.DESC, "created_date"));
        return query;
    }

    public static void sortBy(Query query, String sorting) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> queryFilterMap;
        if (StringUtils.isEmpty(sorting)) {
            getDefaultSorting(query);
            return;
        }
        try {
            sorting = URLDecoder.decode(sorting, StandardCharsets.UTF_8);
            queryFilterMap = objectMapper.readValue(sorting, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception e) {
            Logger.error("Error : " + "ExceptionUtils.getStackTrace(e)");
            getDefaultSorting(query);
            return;
        }
    }

    public static Criteria setStartDateAndEndDate(Long startDate, Long endDate, String fieldName) {
        if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
            return Criteria.where(fieldName).gte(startDate).lte(endDate);
        } else if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            return Criteria.where(fieldName).gte(0L).lte(Instant.now().getEpochSecond());
        } else if (!Objects.isNull(startDate)) {
            return Criteria.where(fieldName).gte(0L).lte(startDate);
        } else {
            return Criteria.where(fieldName).gte(0L).lte(endDate);
        }
    }

    public static String generateKafkaTopicKey(String topicName) {
        return "KEY_" + generateUUID() + topicName;
    }
}
