package com.example.User.api.student.model.request;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDisplayOrderRequest extends CommonAPIDataRequest {

    @JsonProperty("integration_type")
    private Integer integration_type;

    @JsonProperty("data")
    private Map<String,Integer> data;

}