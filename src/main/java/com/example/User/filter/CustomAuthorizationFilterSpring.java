package com.example.User.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.User.constant.AppConstants;
import com.example.User.constant.HeaderConstants;
import com.example.User.database.RedisUserSecretDao;
import com.example.User.database.UserAuth;
import com.example.User.database.UserAuthToken;
import com.example.User.database.UserAuthTokenQueryDao;
import com.example.User.helper.MutableHttpServletRequest;
import com.example.User.helper.UserUtility;
import com.example.User.utils.Logger;
import com.example.User.utils.SecurityUtility;
import com.example.User.utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.logstash.logback.encoder.org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static java.util.Arrays.stream;


public class CustomAuthorizationFilterSpring extends OncePerRequestFilter {
    private final RedisUserSecretDao redisUserSecretDao;
    private final UserUtility authUtility;

    private  final UserAuthTokenQueryDao userAuthTokenQueryDao;

    public CustomAuthorizationFilterSpring(RedisUserSecretDao redisUserSecretDao, UserUtility authUtility, UserAuthTokenQueryDao userAuthTokenQueryDao) {
        this.redisUserSecretDao = redisUserSecretDao;
        this.authUtility = authUtility;
        this.userAuthTokenQueryDao = userAuthTokenQueryDao;
    }

    private String encryptedToken = "";
    private Map<String, Object> map = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        Optional<UserAuth> sub = Optional.empty();
        try {
            MDC.put("traceId", request.getHeader("RequestID")); // Add traceId to logging context
            Logger.info("Trace id :"+request.getHeader("RequestID"));
            encryptedToken = request.getHeader(HeaderConstants.AUTHORIZATION).split(" ")[1];
            String decodedBase64 = new String(new Base64().decode(encryptedToken.split("\\.")[1].getBytes()));
            map = new ObjectMapper().readValue(decodedBase64, new TypeReference<>() {
            });
            sub = redisUserSecretDao.findById(String.valueOf(map.get("sub")));
            if (sub.isEmpty() || StringUtils.isEmpty(sub.get().getSecret_key())
                    || !sub.get().getUser_token().contains(encryptedToken)) {
                UserAuthToken userAuthToken = userAuthTokenQueryDao.findById(String.valueOf(map.get("sub")));
                if (userAuthToken == null || StringUtils.isEmpty(userAuthToken.getSecretKey())
                        || !userAuthToken.getUserToken().contains(encryptedToken)) {
                    this.sendErrorResponse(response);
                    return;
                }
                else{
                    UserAuth redisUserSecret = new UserAuth();
                    redisUserSecret.setUser_id(userAuthToken.getUserId());
                    redisUserSecret.setSecret_key(userAuthToken.getSecretKey());
                    redisUserSecret.setUser_token(userAuthToken.getUserToken());
                    redisUserSecret.setNumber_of_sessions_active(1);
                    redisUserSecret.setCreated_date(Instant.now().getEpochSecond());
                    redisUserSecret.setUpdated_date(Instant.now().getEpochSecond());
                    redisUserSecretDao.save(redisUserSecret);
                    sub = Optional.of(redisUserSecret);
                }
            }
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(sub.get().getSecret_key().getBytes())).build()
                    .verify(encryptedToken);
            String userName = decodedJWT.getSubject();

            Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

            stream(decodedJWT.getClaim(AppConstants.ROLES).asArray(String.class))
                    .forEach(role -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role)));
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthorities));
            MutableHttpServletRequest mutableHttpServletRequest = new MutableHttpServletRequest(request);
            mutableHttpServletRequest.putHeader(HeaderConstants.TOKEN_USER_ID, userName);
            mutableHttpServletRequest.putHeader(HeaderConstants.TOKEN_USER_TYPE, String.valueOf
                    (SecurityUtility.getUserTypeFromTheRole(String.valueOf
                            (simpleGrantedAuthorities.toArray()[0]))));
            mutableHttpServletRequest.putHeader(HeaderConstants.REMOTE_ADDRESS, request.getRemoteAddr());
            MDC.put("userId",userName);
            Logger.info("User id :"+  MDC.get("userId"));
            filterChain.doFilter(mutableHttpServletRequest, response);
        } catch (SignatureVerificationException | NullPointerException | ArrayIndexOutOfBoundsException
                 | JWTDecodeException | AlgorithmMismatchException signatureVerificationException) {
            this.sendErrorResponse(response);
            Logger.error("Error : " + ExceptionUtils.getStackTrace(signatureVerificationException));
        } catch (TokenExpiredException tokenExpiredException) {
            Logger.error("Error : " + ExceptionUtils.getStackTrace(tokenExpiredException));
            authUtility.sendRemoveExpiredTokenDataToProducer(encryptedToken, map.get("sub").toString(),
                    request.getHeader(HeaderConstants.REQUEST_ID) == null ? Utils.generateUUID()
                            : request.getHeader(HeaderConstants.REQUEST_ID), sub.orElse(null));
            this.sendErrorResponse(response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
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
}