package com.example.User.api.trial.controller;

import com.example.User.api.trial.handler.TrialResourceHandler;
import com.example.User.api.trial.model.request.TestModelPutRequest;
import com.example.User.api.trial.model.request.TestModelRequest;
import com.example.User.constant.APIRequestURL;
import com.example.User.constant.AppConstants;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${app.baseurl}" + APIRequestURL.API_BASE_URL)
public class TrialController {

    @Autowired
    private TrialResourceHandler trialResourceHandler;

    @PostMapping(APIRequestURL.TRIAL_API)
    public ResponseEntity<JsonNode> test(@RequestBody TestModelRequest testModelRequest, @RequestHeader HttpHeaders headers) {
        return trialResourceHandler.test(headers, testModelRequest, AppConstants.HTTPMETHODS.POST);
    }

    @GetMapping(APIRequestURL.TRIAL_API)
    public ResponseEntity<JsonNode> testAll(@ModelAttribute TestModelRequest testModelRequest, @RequestHeader HttpHeaders headers) {
        return trialResourceHandler.test(headers, testModelRequest, AppConstants.HTTPMETHODS.GET_ALL);
    }

    @GetMapping(APIRequestURL.TRIAL_API_DETAIL)
    public ResponseEntity<JsonNode> testGetDetail(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        TestModelRequest testModelRequest = new TestModelRequest();
        testModelRequest.setId(id);
        return trialResourceHandler.test(headers, testModelRequest, AppConstants.HTTPMETHODS.GET);
    }

    @DeleteMapping(APIRequestURL.TRIAL_API_DETAIL)
    public ResponseEntity<JsonNode> testDelete(@PathVariable(name = "id") String id, @ModelAttribute TestModelRequest testModelRequest, @RequestHeader HttpHeaders headers) {
        testModelRequest.setId(id);
        return trialResourceHandler.test(headers, testModelRequest, AppConstants.HTTPMETHODS.DELETE);
    }

    @PutMapping(APIRequestURL.TRIAL_API)
    public ResponseEntity<JsonNode> testPut(@RequestBody Map<String, Object> testModelRequest, @RequestHeader HttpHeaders headers) {
        TestModelPutRequest testModelRequest1 = new TestModelPutRequest();
        testModelRequest1.setStringObjectMap(testModelRequest);
        return trialResourceHandler.test(headers, testModelRequest1, AppConstants.HTTPMETHODS.PUT);
    }

}
