package com.example.User.constant;

public interface APIRequestURL {
    public static final String API_BASE_URL = "student/";

    String STUDENT_POST_PUT_GET_ALL_API = "user";
    String STUDENT_POST_DELETE_GET_API = "user/{id}";

    String MFA_CONFIG_POST_PUT_GET_ALL_API = "mfa_configs";
    String MFA_CONFIG_POST_DELETE_GET_API = "mfa_configs/{id}";

    String STUDENT_POST_UPLOAD_FILE_API = "file";
    String SWITCH_STATE_MACHINE = "switch/{id}";
    String AUTH_GET_API_MAINTENANCE_URI = "maintenance";
    String AUTH_MAINTENANCE_MODE_ON = "maintenance_on";
    String AUTH_MAINTENANCE_MODE_OFF = "maintenance_off";
    String DIGIPAY_AUTH_DOCKER_API_URL = "/dev/" + API_BASE_URL;
    String TRIAL_API = "test";
    String TRIAL_API_DETAIL = "test/{id}";

    public static final String[] postByPassApiEndpoints = {
             DIGIPAY_AUTH_DOCKER_API_URL + "maintenance"

    };

    public static final String[] putByPassApiEndpoints = {
            DIGIPAY_AUTH_DOCKER_API_URL + AUTH_MAINTENANCE_MODE_OFF};

    public static final String[] getByPassApiEndpoints = {DIGIPAY_AUTH_DOCKER_API_URL + TRIAL_API
            , DIGIPAY_AUTH_DOCKER_API_URL + AUTH_GET_API_MAINTENANCE_URI
            , DIGIPAY_AUTH_DOCKER_API_URL + SWITCH_STATE_MACHINE};

    public static final String[] deleteByPassApiEndpoints = {DIGIPAY_AUTH_DOCKER_API_URL + TRIAL_API + "/**"};

}
