package com.example.User.api.student.service;

import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.example.User.api.student.model.request.*;
import com.example.User.api.student.model.response.GetAllUserResponse;
import com.example.User.api.student.model.response.GetUserResponse;
import com.example.User.api.student.model.response.SaveUserResponse;
import com.example.User.api.student.model.response.UploadFileResponse;

public interface UserService{
    SaveUserResponse saveUserResponse(SaveUserRequest saveUserRequest);
    GetAllUserResponse getAllUserResponse(GetAllUserRequest UserRequest);
    GetUserResponse getUserResponse(GetUserRequest UserRequest);
    CommonAPIDataResponse deleteUserResponse(GetUserRequest UserRequest);
    CommonAPIDataResponse updateUserResponse(UpdateUserRequest UserRequest);
    UploadFileResponse processUpload(UploadFileRequest uploadExcelFileRequest);
}



//package com.example.User.service;
//
//import com.example.User.Dao.UserRepository;
//import com.example.User.database.User;
//import com.example.User.handler.UserHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserHandler userHandler;
//
//    public User createUser(User user) {
//        User us = User.builder()
//                .name(user.getName())
//                .rollNumber(user.getRollNumber())
//                .build();
//        userHandler.validateUser(user);
//        return userRepository.save(user);
//    }
//
//    public User getUserById(String id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @Transactional
//    public User updateUser(String id, User user) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
//
//        existingUser.setName(user.getName());
//        existingUser.setRollNumber(user.getRollNumber());
//userHandler.validateUpdateUser(id,user);
//        return userRepository.save(existingUser);
//    }
//
//    public void deleteUser(String id) {
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("User not found with id " + id);
//        }
//        userRepository.deleteById(id);
//    }
//}