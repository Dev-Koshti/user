package com.example.User.database;

import com.example.User.constant.AppConstants;
import com.example.User.constant.Tables;
import com.example.User.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Document(collection = Tables.USER_MASTER)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMasterData extends CommonFieldModel {

    @Field("_id")
    @Builder.Default
    private String id = Utils.generateUUID();

    @Field("email")
    private String email = null;

    @Field("password")
    private String password = null;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName = null;

    @Field("dial_code")
    private String dialCode = null;

    @Field("phone_number")
    private String phoneNumber = null;

    @Field("login_pin")
    private String loginPin = null;

    @Field("transaction_pin")
    private String transactionPin = null;

    @Field("is_superuser")
    @Builder.Default
    private Boolean isSuperUser = false;

    @Field("user_type")
    private Integer userType;

    @Field("is_staff")
    @Builder.Default
    private Boolean isStaff = false;

    @Field("role_id")
    private String roleId = null;

    @Field("account_number")
    private String accountNumber = null;

    @Field("verification")
    private Map<String, Object> verification;

    @Field("status_id")
    @Builder.Default
    private Integer statusId = AppConstants.USER_STATUS_ACTIVE;

    @Field("birth_date")
    private Long birthDate;

    @Field("image")
    private String image;

    @Field("country_id")
    private String countryId;

    @Field("rating")
    private Double rating;

    @Field("kyc_status")
    private Integer kycStatus;

    @Field("kyc_process_status")
    private Integer kycProcessStatus;

    @Field("user_code")
    private String userCode;

    @Field("location_point")
    private Map<String, Object> locationPoint;

    @Field("device_info")
    private Map<String, Object> deviceInfo;

    @Field("parent_user_id")
    private String parentUserId;

    @Field("display_name")
    private String displayName;

    @Field("display_image")
    private String displayImage;

    @Field("unique_device_id")
    private String uniqueDeviceId;

}