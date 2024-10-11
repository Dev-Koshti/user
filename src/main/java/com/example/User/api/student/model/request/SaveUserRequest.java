package com.example.User.api.student.model.request;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class SaveUserRequest extends CommonAPIDataRequest {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("contact")
    private String contact;

    public Boolean checkBadRequest(){
        return StringUtils.isEmpty(this.rollNumber)
                || StringUtils.isEmpty(this.name);
    }
}
