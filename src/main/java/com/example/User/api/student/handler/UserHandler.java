package com.example.User.api.student.handler;

import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.example.User.api.student.model.request.*;
import com.example.User.api.student.model.response.GetAllUserResponse;
import com.example.User.api.student.model.response.GetUserResponse;
import com.example.User.api.student.model.response.SaveUserResponse;
import com.example.User.api.student.model.response.UploadFileResponse;
import com.example.User.api.student.service.UserService;
import com.example.User.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;


    public ResponseEntity<JsonNode> saveUser(SaveUserRequest saveUserRequest) {
        if (saveUserRequest.checkBadRequest()) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
        }
        SaveUserResponse saveUserResponse =
                userService.saveUserResponse(saveUserRequest);
        return new ResponseEntity<>(Utils.generateSuccessResponse(saveUserResponse), HttpStatus.OK);
    }

    public ResponseEntity<JsonNode> updateUser(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.checkBadRequest()) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
        }
        CommonAPIDataResponse updateUserResponse =
                userService.updateUserResponse(updateUserRequest);
        return new ResponseEntity<>(Utils.generateSuccessResponse(updateUserResponse), HttpStatus.OK);
    }

    public ResponseEntity<JsonNode> getUser(GetUserRequest getUserRequest) {
        if (getUserRequest.checkBadRequest()) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
        }
        GetUserResponse getUserResponse =
                userService.getUserResponse(getUserRequest);
        return new ResponseEntity<>(Utils.generateSuccessResponse(getUserResponse), HttpStatus.OK);
    }

    public ResponseEntity<JsonNode> getAllUser(GetAllUserRequest getAllUserRequest) {
//        if (getAllUserRequest.checkBadRequest()) {
//            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
//        }
        GetAllUserResponse getAllUserResponse =
                userService.getAllUserResponse(getAllUserRequest);
        return new ResponseEntity<>(Utils.generateSuccessResponse(getAllUserResponse), HttpStatus.OK);
    }

    public ResponseEntity<JsonNode> deleteUser(GetUserRequest deleteUserRequest) {
        if (deleteUserRequest.checkBadRequest()) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
        }
        CommonAPIDataResponse deleteUserResponse =
                userService.deleteUserResponse(deleteUserRequest);

        return new ResponseEntity<>(Utils.generateSuccessResponse(deleteUserResponse), HttpStatus.OK);

    }

    public ResponseEntity<JsonNode> uploadFile( UploadFileRequest uploadFileRequest) {
        // Optionally, process headers if needed
//        HeaderProcessingHelper.setRequestHeaders(uploadFileRequest, headers);

        // Validate the request to check for any bad requests
        if (uploadFileRequest.checkBadRequest()) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
        }

        // Process the file upload using the service layer
        UploadFileResponse uploadFileResponse = userService.processUpload(uploadFileRequest);

        // Generate a JSON response using a utility method
        return Utils.getJsonNodeResponseEntity(uploadFileResponse);
    }


}
//    public void validateUser(User user) {
//        if (user.getName() == null || user.getName().isEmpty()) {
//            throw new IllegalArgumentException("User name cannot be null or empty");
//        }
//        if (user.getRollNumber() == null || user.getRollNumber().isEmpty()) {
//            throw new IllegalArgumentException("User roll number cannot be null or empty");
//        }
//    }
//
//    public void validateUpdateUser(String id,User user) {
//        if (!Objects.equals(user.getRollNumber(), id)){
//            throw new IllegalArgumentException("RollNumber cannot Be updated so Dont change Rollumber");
//        }
//    }

