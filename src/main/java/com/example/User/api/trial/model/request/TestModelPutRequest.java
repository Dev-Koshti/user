package com.example.User.api.trial.model.request;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TestModelPutRequest extends CommonAPIDataRequest {

    private Map<String, Object> stringObjectMap;
}
