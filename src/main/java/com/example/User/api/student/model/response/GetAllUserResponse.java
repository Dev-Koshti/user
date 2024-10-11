package com.example.User.api.student.model.response;

import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetAllUserResponse extends CommonAPIDataResponse {

    @JsonProperty("user_data")
    List<UserData> userDataList;

}
