package com.example.User.api.trial.handler;

import com.example.User.constant.HeaderConstants;
import com.example.User.helper.HeaderProcessingHelper;
import com.example.User.helper.UserUtility;
import com.example.User.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TrialResourceHandler {

    public ResponseEntity<JsonNode> test(HttpHeaders headers, Object testModelRequest, int method) {
        HeaderProcessingHelper.setRequestHeaders(testModelRequest, headers);
        if (!UserUtility.checkSumVerify(testModelRequest, headers.getFirst(HeaderConstants.SIGNATURE), method)) {
            return new ResponseEntity<>(UserUtility.sendUnAuthorized(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(Utils.generateSuccessResponse(testModelRequest), HttpStatus.OK);
    }
}
