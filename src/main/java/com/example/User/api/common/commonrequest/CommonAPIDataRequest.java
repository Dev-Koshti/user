package com.example.User.api.common.commonrequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonAPIDataRequest {

    public String company_id;

    private String token_user_id;

}