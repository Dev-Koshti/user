package com.example.User.api.student.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class GetAllUserRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("search_keyword")
    private String search_keyword;



}
