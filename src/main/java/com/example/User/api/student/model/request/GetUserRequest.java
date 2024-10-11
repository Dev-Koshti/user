package com.example.User.api.student.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
@NoArgsConstructor
public class GetUserRequest {

    @JsonProperty("_id")
    private String id;

    public Boolean checkBadRequest() {
        return StringUtils.isEmpty(this.getId());
    }

}
