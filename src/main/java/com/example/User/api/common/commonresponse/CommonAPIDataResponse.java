package com.example.User.api.common.commonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties({"validationMessage", "checkValidationFailed", "checkForUnAuthorized"})
public class CommonAPIDataResponse {

    @JsonProperty("validationMessage")
    private String validationMessage;

    @JsonProperty("checkValidationFailed")
    private boolean checkValidationFailed;

    @JsonProperty("checkForUnAuthorized")
    private boolean checkForUnAuthorized;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("message")
    private String message;
}
