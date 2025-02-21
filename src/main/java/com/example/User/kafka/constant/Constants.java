package com.example.User.kafka.constant;


public interface Constants {

    String KAFKA_TOPIC_USER_SETUP = "dig_topic_user_setup";
    String KAFKA_TOPIC_USER_BULK_ON_BOARD = "dig_topic_user_bulk_on_board";
    String KAFKA_TOPIC_USER_BULK_ON_BOARD_MERCHANT_BUSINESS = "dig_topic_user_bulk_on_board_merchant_business";
    String KAFKA_TOPIC_USER_BULK_ON_BOARD_AGENT_LEGAL_DETAIL = "dig_topic_user_bulk_on_board_agent_legal_detail";
    String KAFKA_TOPIC_ELASTIC_SEARCH_ACTIVITY_LOGS = "dig_topic_elastic_search_activity_logs";
    String KAFKA_TOPIC_REFERRAL_ON_REFER = "dig_topic_referral_on_refer";
    String KAFKA_TOPIC_REWARD_ON_USER_SIGNUP = "dig_topic_reward_on_user_signup";
    String KAFKA_TOPIC_COMMON_API_LOGS = "dig_topic_common_api_logs";
    String KAFKA_GROUP_AUTH_DOCKER = "dig_auth_docker_group";
    String KAFKA_TOPIC_SENT_OTP = "dig_topic_sent_otp";
    String KAFKA_TOPIC_SEND_NOTIFICATION = "dig_topic_send_notification";
    String KAFKA_TOPIC_REMOVE_TOKEN_FROM_REDIS_AUTH_TABLE = "dig_topic_remove_token";

    String KAFKA_TOPIC_FLINK_LOGIN_FAILED = "dig_topic_flink_login_failed";
    String KAFKA_TOPIC_TESTING_FLINK_BLOCK_USER = "dig_topic_fraud_detect_block_users";
    String KAFKA_TOPIC_TESTING_FLINK_UNBLOCK_USER = "dig_topic_fraud_detect_unblock_users";

    String KAFKA_TOPIC_SEND_DATA_TO_DATADOG = "dig_topic_send_logs_to_data_dog_auths";

    String KAFKA_TOPIC_RECEIVE_EVENT_LOGS_INSERT = "dip_topic_event_logs";

}