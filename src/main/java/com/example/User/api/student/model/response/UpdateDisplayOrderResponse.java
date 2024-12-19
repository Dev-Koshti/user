package com.example.User.api.student.model.response;

import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDisplayOrderResponse extends CommonAPIDataResponse {
    @JsonProperty("update_display_order")
    private String updateDisplayOrder;
}