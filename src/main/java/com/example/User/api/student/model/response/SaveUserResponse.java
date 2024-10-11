package com.example.User.api.student.model.response;

import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveUserResponse  extends CommonAPIDataResponse {

    @JsonProperty("user_id")
    private String userId;

}
