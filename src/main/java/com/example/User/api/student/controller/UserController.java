package com.example.User.api.student.controller;

import com.example.User.api.student.handler.UserHandler;
import com.example.User.api.student.model.request.*;
import com.example.User.constant.APIRequestURL;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("${app.baseurl}")
@RequiredArgsConstructor
public class UserController {

  private final UserHandler userHandler;


    @PostMapping(APIRequestURL.STUDENT_POST_PUT_GET_ALL_API)
    public ResponseEntity<JsonNode> addUser(@RequestBody SaveUserRequest saveUserRequest) {
        return userHandler.saveUser(saveUserRequest);
//        try {
//            User us = userHandler.createUser(user);
//            return ResponseEntity.ok(us);
//        } catch (IllegalArgumentException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    } catch (Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
//    }
    }


    @GetMapping(APIRequestURL.STUDENT_POST_PUT_GET_ALL_API)
    public ResponseEntity<JsonNode> getAllUser(@ModelAttribute GetAllUserRequest getUserRequest){
        return userHandler.getAllUser(getUserRequest);
    }

    @GetMapping(APIRequestURL.STUDENT_POST_DELETE_GET_API)
    public ResponseEntity<JsonNode> getUser(@PathVariable String id,@ModelAttribute GetUserRequest getUserRequest){
        getUserRequest.setId(id);
        return userHandler.getUser(getUserRequest);
    }

    @PutMapping(APIRequestURL.STUDENT_POST_PUT_GET_ALL_API)
    public ResponseEntity<JsonNode> updateUser(@RequestBody UpdateUserRequest updateUser) {
//        try {
        return userHandler.updateUser(updateUser);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
//        }
    }

    @DeleteMapping(APIRequestURL.STUDENT_POST_DELETE_GET_API)
    public ResponseEntity<JsonNode> deleteUser(@PathVariable String id,@ModelAttribute GetUserRequest deleteUserRequest) {
        deleteUserRequest.setId(id);
        return userHandler.deleteUser(deleteUserRequest);
    }

    @PostMapping(APIRequestURL.STUDENT_POST_UPLOAD_FILE_API)
    public ResponseEntity<JsonNode> uploadFile(@ModelAttribute UploadFileRequest uploadRequest) {
        return userHandler.uploadFile(uploadRequest);
    }

    @PutMapping("banks")
    public ResponseEntity<JsonNode> updateDisplayOrder(@RequestHeader HttpHeaders headers, @RequestBody UpdateDisplayOrderRequest updateDisplayOrderRequest) {
        return userHandler.updateDisplayOrder( updateDisplayOrderRequest);
    }
}
