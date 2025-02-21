package com.example.User.api.trial.model.request;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TestModelRequest extends CommonAPIDataRequest {


    private String field1;
    private Long field3;
    private Double field2;
    private String id;
    private Map<String, Object> field_map;
    private List<Map<String, Object>> field_list1;
    private List<String> field_list2;
    private TestObject test_object;
    private String query_filter;
}
