package com.example.User.constant;

public interface AppConstants {

    public static final String ENGLISH_LANGUAGE = "en";

    // HTTP Methods
    public static final String HTTP_GET_METHOD = "GET";
    public static final String HTTP_POST_METHOD = "POST";
    public static final String HTTP_PUT_METHOD = "PUT";
    public static final String HTTP_DELETE_METHOD = "DELETE";

    //Yes OR No
    public static final String YES = "Y";
    public static final String NO = "N";

    // Success & UnSuccess
    public static final int SUCCESSFUL = 1;
    public static final int UNSUCCESSFUL = 0;

    // Module Settings Group Key
    public static final String GROUP_TYPE_SMS_SETTINGS = "sms_settings";
    public static final String GROUP_TYPE_EMAIL_SETTINGS = "email_settings";
    public static final String GROUP_TYPE_MAPS_SETTINGS = "map_settings";
    public static final String GROUP_TYPE_TRANSACTION_SETTINGS = "transaction_settings";


    // Module Settings Module Key
    public static final String MODULE_KEY_SENDGRID = "module_sendgrid";
    public static final String MODULE_KEY_SMTP = "module_smtp";
    public static final String MODULE_KEY_TWILIO = "module_twilio";
    public static final String MODULE_KEY_LAM = "module_lam";
    public static final String MODULE_KEY_DIGIPAY_AFRICA_REST_SMS = "module_digipay_africa_rest_sms";

    //Company Postal Code Constant
    public static final Integer POSTAL_ALPHA_NUMERIC = 3;

    //Common API Request Reflect Method Name
    public static final String COMPANY_ID = "company_id";
    public static final String TYPE = "type";
    public static final String TOKEN = "token";
    public static final String REQUEST_ID = "request_id";
    public static final String IP_ADDRESS = "ip_address";
    public static final String LANGUAGE = "language";

    Integer REQUEST_FOR_APPROVERS = 168;
    Integer EVENT_TYPE_CHAKER_REQUEST_REJECT = 202;
    Integer EVENT_TYPE_CHAKER_REQUEST_ACCEPT = 203;
    Integer REQUEST_FOR_MAKER_USER = 211;
    Integer EVENT_TYPE_USER_REQUEST_TO_MAKER = 204;

    // Common API Response Method Name
    public static final String SET_CHECK_FOR_UNAUTHORIZED = "setCheckForUnAuthorized";
    public static final String SET_VALIDATION_MESSAGE = "setValidationMessage";
    public static final String SET_CHECK_FOR_VALIDATION_FAILED = "setCheckValidationFailed";


    //User Status Types
    public static final int USER_STATUS_ACTIVE = 1;
    public static final int USER_STATUS_INACTIVE = 2;
    public static final int USER_STATUS_ONBOARD = 3;
    public static final int USER_STATUS_BLOCKED = 4;
    public static final int USER_STATUS_SUSPENDED = 5;

    //User Account Types
    public static final int ACCOUNT_TYPE_ADMIN = 1;
    public static final int ACCOUNT_TYPE_USER = 2;
    public static final int ACCOUNT_TYPE_MERCHANT = 3;
    public static final int ACCOUNT_TYPE_AGENT = 5;

    //Config key
    public static final String SMTP_FROM_EMAIL_CONFIG_KEY = "smtp_from_email";
    public static final String SMTP_PASSWORD_CONFIG_KEY = "smtp_password";
    public static final String SMTP_USERNAME_CONFIG_KEY = "smtp_username";
    public static final String SMTP_PORT_CONFIG_KEY = "smtp_port";
    public static final String SMTP_HOST_CONFIG_KEY = "smtp_host";
    public static final String ANDROID_SERVER_API_CONFIG_KEY = "android_server_api_key";
    public static final String NO_OF_DECIMALS_CONFIG_KEY = "no_of_decimals";
    public static final String CURRENCY_AMOUNT_SEPARATOR_CONFIG_KEY = "currency_amount_separator";
    public static final String CURRENCY_POSITION_CONFIG_KEY = "currency_position";

    public static final String AFTER_CONFIG_VALUE = "after";

    public static final String TWILIO_ACC_SID_CONFIG_KEY = "twilio_account_sid";
    public static final String TWILIO_PHONE_NO_CONFIG_KEY = "twilio_phone_number";
    public static final String TWILIO_AUTH_TOKEN_CONFIG_KEY = "twilio_auth_token";
    public static final String TWILIO_MESSAGE_SERVICE_ID_CONFIG_KEY = "twilio_message_service_id";

    public static final String SENDGRID_FROM_EMAIL_CONFIG_KEY = "sendgrid_from_email";
    public static final String SENDGRID_PASSWORD_CONFIG_KEY = "sendgrid_password";

    public static final String EVENT_TYPE = "event_type";
    public static final String ENABLE = "enable";
    public static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

    public static final int PRICE_TYPE_RECURRING = 1;

    //microsoft graph api
    public static final String MODULE_KEY_MICROSOFT_GRAPH = "module_microsoft_graph";
    public static final String MICROSOFT_GRAPH_TENANT_ID = "microsoft_graph_tenant_id";
    public static final String MICROSOFT_GRAPH_SEND_MAIL_URL = "microsoft_graph_send_mail_api_url";
    public static final String MICROSOFT_GRAPH_APP_TOKEN_URL = "microsoft_graph_access_token_api_uri";
    public static final String MICROSOFT_GRAPH_CLIENT_SECRET = "microsoft_graph_client_secret";
    public static final String MICROSOFT_GRAPH_SCOPE = "microsoft_graph_token_api_scope";
    public static final String MICROSOFT_GRAPH_CLIENT_ID = "microsoft_graph_client_id";
    public static final String MICROSOFT_GRAPH_USERNAME = "microsoft_graph_username";
    public static final String MICROSOFT_GRAPH_PASSWORD = "microsoft_graph_password";
    public static final String MICROSOFT_GRAPH_GRANT_TYPE = "microsoft_graph_token_api_grant_type";
    public static final String MICROSOFT_GRAPH_ACCESS_TOKEN_HOST = "microsoft_graph_token_api_host";
    public static final String MICROSOFT_GRAPH_USER_ID = "microsoft_graph_user_id";
    public static final String TENANT_ID = "tenantId";
    public static final String USER_ID = "userId";

    //send sms infobip
    public static final String MODULE_INFOBIP_SMS = "module_infobip_sms";
    public static final String MODULE_INFOBIP_APP_NAME = "module_app_name";
    public static final String MODULE_INFOBIP_APP_TOKEN = "module_app_token";
    public static final String MODULE_INFOBIP_SMS_API_URI = "module_sms_api_url";

    //send bulk sms using viatechapi
    public static final String MODULE_KEY_VIA_TECH_BULK_SMS = "module_via_tech_bulk_sms";
    public static final String VIATECH_BULK_SMS_USERNAME = "viatech_bulk_sms_username";
    public static final String VIATECH_BULK_SMS_APIKEY = "viatech_bulk_sms_api_key";
    public static final String VIATECH_BULK_SMS_APISECRET = "viatech_bulk_sms_api_secret";
    public static final String VIATECH_BULK_SMS_SMS_DATA_MASK = "viatech_bulk_sms_data_mask";
    public static final String VIATECH_BULK_SMS_URI = "viatech_bulk_sms_url";

    //Transaction Code
    public static final String TXN_PRODUCT_CODE_CASH_IN_BANK = "CIB";
    public static final String TXN_PRODUCT_CODE_AGENT_CASH_IN_BANK = "ACIB";
    public static final String TXN_PRODUCT_CODE_P2P_TRANSFER_MONEY = "P2P";
    public static final String TXN_PRODUCT_CODE_INTER_WALLET_TRANSFER_MONEY = "IWT";
    public static final String TXN_PRODUCT_CODE_CASH_OUT_BANK = "COB";
    public static final String TXN_PRODUCT_CODE_MERCHANT_SETTLEMENT = "MS";
    public static final String TXN_PRODUCT_CODE_AGENT_SETTLEMENT = "AS";
    public static final String TXN_PRODUCT_CODE_AGENT_CASH_OUT_BANK = "ACOB";
    public static final String TXN_PRODUCT_CODE_MERCHANT_REFUND = "MR";
    public static final String TXN_PRODUCT_CODE_CUSTOMER_REFUND = "CR";
    public static final String TXN_PRODUCT_CODE_ADMIN_WALLET_TOPUP = "AWT";
    public static final String TXN_PRODUCT_CODE_ADMIN_CREATE_E_MONEY = "ACM";
    public static final String TXN_PRODUCT_CODE_CASH_IN_VIA_AGENT = "CIA";
    public static final String TXN_PRODUCT_CODE_CASH_OUT_VIA_AGENT = "COA";
    public static final String TXN_PRODUCT_CODE_M2M_TRANSFER_MONEY = "M2M";

    public static final String TXN_PRODUCT_CODE_CASH_IN_FOR_CUSTOMER_VIA_AGENT = "CIC";
    public static final String TXN_PRODUCT_CODE_CASH_OUT_FOR_CUSTOMER_VIA_AGENT = "COC";
    String TXN_PRODUCT_CODE_CASH_OUT_FOR_MERCHANT_VIA_AGENT = "COM";

    public static final String TXN_PRODUCT_CODE_A2A_TRANSFER = "A2A";
    public static final String TXN_PRODUCT_CODE_CTA_TRANSFER = "CTA";
    public static final String TXN_PRODUCT_CODE_CTBA_TRANSFER = "CTBA";
    public static final String TXN_PRODUCT_CODE_PAY_MERCHANT = "P2M";
    public static final String TXN_PRODUCT_CODE_PAY_MERCHANT_DEVICE = "P2MD";
    public static final String TXN_PRODUCT_CODE_NFC_PAYMENT = "NFCP";
    public static final String TXN_PRODUCT_CODE_CUSTOMER_SETTLEMENT = "CS";
    public static final String TXN_PRODUCT_CODE_MERCHANT_SETTLEMENT_COMMISION = "MC";
    public static final String TXN_PRODUCT_CODE_AGENT_SETTLEMENT_COMMISION = "AC";
    public static final String TXN_USER_TRANSACTION_COMMISION = "UTC";
    public static final String TXN_USER_TRANSACTION_REFERRAL_REWARD = "RRW";
    public static final String TXN_USER_THIRD_PARTY_TRANSACTION = "TPT";
    public static final String TXN_USER_TRANSACTION_REWARD_CASHBACK_REDEEM = "RCR";
    public static final String TXN_ADMIN_CHARGE = "ATC";
    public static final String TXN_PRODUCT_CODE_INTERNATIONAL_TRANSFER_MONEY = "IR";
    public static final String TXN_PRODUCT_CODE_PAY_FOR_PARKING = "PFP";
    public static final String TXN_PRODUCT_CODE_USER_TRANSACTION_REFUND = "UTR";
    public static final String TXN_PRODUCT_CODE_MOBILE_RECHARGE = "MPR";
    public static final String TXN_PRODUCT_CODE_MOBILE_BILL_PAYMENT = "MBP";
    public static final String TXN_GPS_CAMPAIGN_SUBSCRIBE_PLAN = "GCSP";
    public static final String TXN_LOAN_REPAYMENT = "LRT";
    public static final String TXN_AGENT_LOAN_REPAYMENT = "ALR";
    public static final String TXN_LOAN_AMOUNT_PAID = "LAP";
    public static final String TXN_PRODUCT_CODE_VOUCHER_PURCHASE = "VPT";
    public static final String TXN_PRODUCT_CODE_FOOD_VOUCHER_PURCHASE = "SFVP";
    public static final String TXN_PRODUCT_CODE_PURCHASE_VOUCHER_CREDIT = "PVC";
    public static final String TXN_PRODUCT_CODE_REFUND_VOUCHER_CREDIT = "RVC";
    public static final String RECURRING_SUBSCRIPTION_PAYMENT = "RSP";

    public static final String TXN_PRODUCT_CODE_NRTA_TRANSFER = "NRTA";
    public static final String TXN_PRODUCT_CODE_FUND_VIRTUAL_CARD = "FVC";
    public static final String TXN_PRODUCT_CODE_WITHDRAW_VIRTUAL_CARD = "WVC";
    public static final String TXN_PRODUCT_CODE_CUSTOMER_REGISTRATION_FEE = "CRF";
    public static final String TXN_PRODUCT_CODE_MOBILE_TOPUP_VOUCHER = "MTV";
    public static final String TXN_PRODUCT_CODE_TRANSFER_THIRD_PARTY_WALLET = "TTPW";
    public static final String TXN_PRODUCT_CODE_SUB_WALLET_TOPUP = "SWT";
    public static final String TXN_PRODUCT_CODE_SUB_WALLET_TRANSFER = "SWTR";
    public static final String TXN_PRODUCT_CODE_SUB_WALLET_WITHDRAW = "SWW";
    public static final String TXN_PRODUCT_CODE_CASH_IN_VIA_MASTER_AGENT = "CIMA";
    public static final String TXN_PRODUCT_CODE_CASH_OUT_VIA_MASTER_AGENT = "COMA";
    public static final String TXN_PRODUCT_CODE_CORPORATE_BULK_PAYMENT = "CBP";
    String TXN_PRODUCT_CODE_CARD_TOPUP = "CDT";
    String TXN_PRODUCT_CODE_CARD_PAYOUT = "CDP";
    String TXN_PRODUCT_CODE_CARD_TRANSFER = "CDTR";
    String TXN_PRODUCT_CODE_NON_REGISTER_TO_REGISTER = "NRTR";
    String TXN_PRODUCT_CODE_NFC_CARD_PAYMENT = "NCP";
    String TXN_PRODUCT_CODE_COMMISSION_TRANSFER = "CT";
    String TXN_PRODUCT_CODE_COMMISSION_BANK_TRANSFER = "CTB";
    String TXN_PRODUCT_CODE_COMMISSION_WALLET_TRANSFER = "CTW";
    String TXN_PRODUCT_CODE_USER_WALLET_DEDUCTION = "UWD";
    String TXN_PRODUCT_CODE_UTILITY_REFUND = "UTR";
    String TXN_PRODUCT_CODE_STORE_SETTLEMENT = "SS";


    //Spring security
    public static final String SECURE_KEY_SPRING_SECURITY = "JASHDBasdes45^**%2IU48EU#%^^S5648s6df4e98rfg4er5fg1evev6";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    public static final String ROLE_MERCHANT = "ROLE_MERCHANT";
    public static final String ROLE_STAFF = "ROLE_STAFF";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    String ROLE_AGENT = "ROLE_AGENT";


    public static final String ROLE_MERCHANT_AS_AGENT = "ROLE_MERCHANT_AS_AGENT";
    public static final String AUTH_TOKEN_KEY = "Token";

    //User Account Types
    public static final int ACCOUNT_TYPE_STAFF = 4;
    public static final String LOG_STATUS_TYPE_ERROR = "Error";
    public static final String LOG_STATUS_TYPE_INFO = "Info";
    public static final String LOG_STATUS_TYPE_WARN = "Warn";
    public static final String ROLES = "roles";
    public static final int ACCOUNT_TYPE_MERCHANT_AS_AGENT = 35;
    public static final int OTP_TYPE_COMMON = 5;

    /*Alchemy*/
    public static final String MODULE_KEY_ALCHEMY = "module_alchemy";
    public static final String ALCHEMY_USER_NAME_CONFIG_KEY = "alchemy_user_name";
    public static final String ALCHEMY_PASSWORD_CONFIG_KEY = "alchemy_password";
    public static final String ALCHEMY_ACTION_CONFIG_KEY = "alchemy_action";
    public static final String ALCHEMY_MESSAGE_TYPE_CONFIG_KEY = "alchemy_message_type";
    public static final String ALCHEMY_API_BASE_URL_CONFIG_KEY = "alchemy_api_base_url";
    public static final String SEND_ALCHEMY_API_BASE_URL = "/api";
    public static final String ALCHEMY_ORIGINATOR_CONFIG_KEY = "alchemy_originator";
    public static final String ALCHEMY_STAR_SYMBOL = "*";
    public static final String ALCHEMY_STAR_REPLACEMENT_SYMBOL = "%2A";
    public static final String EMAIL_SUBJECT = "Notification";

    public static final String DATA_DOG_SERVICE_NAME = "Notification";

    // for regex options
    public static final String IGNORE_CASE_AND_NEW_LINE = "si";

    /*LAM*/
    public static final String LAM_BASE_URL = "lam_base_url";
    public static final String LAM_USER_NAME = "lam_username";
    public static final String LAM_PASSWORD = "lam_password";
    public static final String LAM_SENDER = "lam_sender";

    /*Victory Link SMS Gateway*/
    String MODULE_KEY_VL = "module_vl";
    String VL_BASE_URL = "vl_base_url";
    String VL_USER_NAME = "vl_username";
    String VL_PASSWORD = "vl_password";
    String VL_SENDER = "vl_sms_sender";
    String VL_SMS_LAN = "vl_sms_lan";
    String VL_SEND_SMS_API_END_POINT = "/SendSMS";


    //for criteria operation type
    public static final int CRITERIA_OPERATION_TYPE_AND = 1;
    public static final int CRITERIA_OPERATION_TYPE_OR = 2;
    public static final int CRITERIA_OPERATION_TYPE_GTE_AND_LTE = 3;
    public static final int CRITERIA_OPERATION_TYPE_GT_AND_LT = 4;
    public static final int CRITERIA_OPERATION_GT = 5;
    public static final int CRITERIA_OPERATION_LT = 6;
    int CRITERIA_OPERATION_NOT_IN = 7;

    // Notification target type
    public static final int TARGET_TYPE_INDIVIDUAL = 1;
    public static final int TARGET_TYPE_ALL_USERS = 2;

    String KAFKA_BOOTSTRAP_SERVER = "kafka.bootstrap.servers";
    String MONITORING_ALERT_TITLE = "Container Monitoring Alert";
    Integer ADMIN_RESET_PASSWORD_EVENT_TYPE = 999;

    String SUCCESSFUL_CODE_STRING ="0";

    interface HTTPMETHODS {
        int POST = 1;
        int GET_ALL = 2;
        int GET = 3;
        int PUT = 4;
        int DELETE = 5;
    }

    interface ALGORITHM {
        String HMACSHA256 = "HmacSHA256";
    }

    //microsoft graph api
    String MODULE_KEY_BREVO_EMAIL = "module_brevo_mail";
    String BREVO_FROM_MAIL_ID = "brevo_from_mail";
    String BREVO_SEND_MAIL_URL = "brevo_send_mail_api_url";
    String BREVO_APP_TOKEN = "brevo_access_token";
    String BREVO_COMPANY_NAME = "brevo_company_name";

    String DEFAULT_DECIMAL_FORMAT_PATTERN = "###,###.##";

    String CUSTOM_FORMAT_PATTERN = "decimal_format_pattern";
    
    /* zamtel */
    public static String MODULE_KEY_ZAMTEL = "module_zamtel";
    public static String ZAMTEL_BASE_URL="zamtel_base_url";
    public static String ZAMTEL_API_KEY="zamtel_api_key";
    public static String ZAMTEL_SENDER_ID="zamtel_sender_id";

    //Firebase
    public static String PROJECT_ID = "project_id";
    public static String FIREBASE_CLIENT_D = "firebase_client_id";
    public static String FIREBASE_CLIENT_EMAIL = "firebase_client_email";
    public static String FIREBASE_PRIVATE_KEY = "firebase_private_key";
    public static String FIREBASE_PRIVATE_KEY_ID = "firebase_private_key_id";

    //Blueline SMTP Detail
    public static final String MODULE_KEY_BLUELINE = "module_blueline_mail";
    public static final String BLUELINE_SMTP_FROM_EMAIL_CONFIG_KEY = "blueline_from_mail";
    public static final String BLUELINE_SMTP_PASSWORD_CONFIG_KEY = "blueline_smtp_password";
    public static final String BLUELINE_SMTP_USERNAME_CONFIG_KEY = "blueline_smtp_username";
    public static final String BLUELINE_SMTP_PORT_CONFIG_KEY = "blueline_smtp_port";
    public static final String BLUELINE_SMTP_HOST_CONFIG_KEY = "blueline_smtp_server_host";
    public static final String BLUELINE_COMPANY_NAME_CONFIG_KEY = "blueline_company_name";


    //testing SMTP
    String user_name = "miaro@miaro.mg";
    String apikey = "gR3Lb11tR";
    String port = "587";
    String host = "smtp.blueline.mg";
    String from_email = "miaro@miaro.mg";
    String to_email = "jainam.s@digipay.guru";
    String subject = "Test Email";
    String body = "Blueline Just Testing email";

    String MODULE_KEY_BLUELINE_SMS = "module_blueline";
    String BLUELINE_BASE_URL = "blueline_api_base_url";
    String BLUELINE_USER_NAME = "blueline_user_name";
    String BLUELINE_PASSWORD = "blueline_password";
    String BLUELINE_SENDER = "blueline_senderid";
    String BLUELINE_SMS_AUTH_URL = "ldap/v2/auth/request-token";
    String BLUELINE_SMS_SEND_SMS_URL = "smsc/v1/sms";
    String VALUES_OF_AT_SYMBOL = "@";

    String[] DATE_FORMATS = {
            "dd/MM/yyyy", // Index 0 (not used, to align with 1-based indexing)
            "dd/MM/yyyy", // 1
            "dd-MM-yyyy", // 2
            "MM/dd/yyyy", // 3
            "yyyy/MM/dd"  // 4
    };

    String[] TIME_FORMATS = {
            "HH:mm:ss",   // Index 0 (not used)
            "hh:mm:ss a", // 1 (12-hour format with AM/PM)
            "HH:mm:ss"    // 2 (24-hour format)
    };
}