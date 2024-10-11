package com.example.User.api.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@Data
public class ErrorResponse {

    @JsonProperty("success")
    private int success;

    @JsonProperty("error")
    private ArrayList<String> error;

    @JsonIgnore
    @JsonProperty("data")
    private Object data;

}
