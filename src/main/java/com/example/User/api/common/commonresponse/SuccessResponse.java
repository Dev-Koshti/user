package com.example.User.api.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@Data
public class SuccessResponse {

    @JsonProperty("success")
    private int success;

    @JsonProperty("error")
    private ArrayList<String> error;

    @JsonProperty("data")
    private Object data;

    public SuccessResponse(int success, ArrayList<String> error, Object data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }
}
