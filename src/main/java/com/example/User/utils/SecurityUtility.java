package com.example.User.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.User.constant.AppConstants;
import com.example.User.database.UserMasterData;
import com.example.User.helper.SpringUsers;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SecurityUtility {

    public static String generateAccessToken(UserMasterData userMasterData, String secretKey, int expiryTimeStamp) {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        String passWord = userMasterData.getDialCode().concat(userMasterData.getPhoneNumber());
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + userMasterData.getUserType()));
        SpringUsers springUsers = new SpringUsers(userMasterData.getId(), passWord
                , userMasterData.getIsActive(), true, true
                , true, simpleGrantedAuthorities, userMasterData);

        List<String> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(getRoleFromTheUserType(Objects.isNull(springUsers.getUserType()) ?
                AppConstants.ACCOUNT_TYPE_USER : springUsers.getUserType()));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeStamp);
        return JWT.create()
                .withSubject(springUsers.getUsername())
                .withExpiresAt(new Date(calendar.getTimeInMillis()))
                .withClaim(AppConstants.ROLES, grantedAuthorities)
                .sign(algorithm);
    }

    public static String getRoleFromTheUserType(int userType) {
        switch (userType) {
            case AppConstants.ACCOUNT_TYPE_MERCHANT:
                return AppConstants.ROLE_MERCHANT;
            case AppConstants.ACCOUNT_TYPE_AGENT:
                return AppConstants.ROLE_AGENT;
            case AppConstants.ACCOUNT_TYPE_STAFF:
                return AppConstants.ROLE_STAFF;
            case AppConstants.ACCOUNT_TYPE_ADMIN:
                return AppConstants.ROLE_ADMIN;
            case AppConstants.ACCOUNT_TYPE_MERCHANT_AS_AGENT:
                return AppConstants.ROLE_MERCHANT_AS_AGENT;
            default:
                return AppConstants.ROLE_CUSTOMER;
        }
    }

    public static Integer getUserTypeFromTheRole(String role) {
        switch (role) {
            case AppConstants.ROLE_MERCHANT:
                return AppConstants.ACCOUNT_TYPE_MERCHANT;
            case AppConstants.ROLE_AGENT:
                return AppConstants.ACCOUNT_TYPE_AGENT;
            case AppConstants.ROLE_STAFF:
                return AppConstants.ACCOUNT_TYPE_STAFF;
            case AppConstants.ROLE_ADMIN:
                return AppConstants.ACCOUNT_TYPE_ADMIN;
            case AppConstants.ROLE_MERCHANT_AS_AGENT:
                return AppConstants.ACCOUNT_TYPE_MERCHANT_AS_AGENT;
            default:
                return AppConstants.ACCOUNT_TYPE_USER;
        }
    }

}