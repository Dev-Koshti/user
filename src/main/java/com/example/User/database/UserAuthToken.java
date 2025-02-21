package com.example.User.database;

import com.example.User.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Document(collection = "user_auth_token")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthToken extends CommonFieldModel {

    @Field("_id")
    @Builder.Default
    private String id = Utils.generateUUID();

    @Field("user_id")
    private String userId;

    @Field("secret_key")
    private String secretKey;

    @Field("user_token")
    private List<String> userToken;

    @Field("number_of_sessions_active")
    private Integer numberOfSessionsActive;

    @Field("token_type")
    private Integer tokenType;


}
