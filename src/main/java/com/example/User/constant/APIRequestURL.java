package com.example.User.constant;

public interface APIRequestURL {
    String STUDENT_POST_PUT_GET_ALL_API = "user";
    String STUDENT_POST_DELETE_GET_API = "user/{id}";

    String MFA_CONFIG_POST_PUT_GET_ALL_API = "mfa_configs";
    String MFA_CONFIG_POST_DELETE_GET_API = "mfa_configs/{id}";

    String STUDENT_POST_UPLOAD_FILE_API = "file";
}
