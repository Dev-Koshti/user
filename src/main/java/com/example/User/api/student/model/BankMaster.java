package com.example.User.api.student.model;

import com.example.User.database.CommonFieldModel;
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

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "bank")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankMaster extends CommonFieldModel {

    @Field("_id")
    @Builder.Default
    private String id = Utils.generateUUID();

    @Field("bank_name")
    private String bankName;

    @Field("bank_image")
    private String bankImage;

    @Field("bank_code")
    private String bankCode;

    @Field("bank_type")
    private Integer bankType;

    @Field("is_master_bank")
    private Boolean isMasterBank;

    @Field("irf_config")
    private Map<String, Object> irfConfig;

    @Field("display_name")
    private String displayName;

    @Field("integration_type")
    private Integer integrationType;

    @Field("integration_configs")
    private Map<String, Object> integrationConfigs;

    @Field("display_order")
    private Integer displayOrder;

}