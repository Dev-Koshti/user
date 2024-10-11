package com.example.User.api.student.service.impl;

import com.example.User.api.student.Dao.UserRepository;
import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.example.User.api.student.model.request.*;
import com.example.User.api.student.model.response.*;
import com.example.User.database.ExcelData;
import com.example.User.database.User;
import com.example.User.api.student.service.UserService;
import com.example.User.kafka.producer.KafkaProducerSend;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private KafkaProducerSend kafkaProducerSend;

    private final UserRepository userRepository;

    @Override
    public SaveUserResponse saveUserResponse(SaveUserRequest saveUserRequest) {
        User user = new User().setUser(saveUserRequest);
        userRepository.save(user);

        SaveUserResponse saveUserResponse = new SaveUserResponse();
        saveUserResponse.setMessage("User saved successfully");
        saveUserResponse.setUserId(String.valueOf(user.getId()));
        return saveUserResponse;
    }

    @Override
    public GetUserResponse getUserResponse(GetUserRequest getUserRequest) {
        User user = userRepository.findById(getUserRequest.getId());

        UserData userData = new UserData().setUserData(user);
        GetUserResponse getUserResponse = new GetUserResponse();
        getUserResponse.setUserData(userData);
        return getUserResponse;
    }

    @Override
    public CommonAPIDataResponse updateUserResponse(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(updateUserRequest.getId());
        CommonAPIDataResponse response = new CommonAPIDataResponse();
        if (user==null){
            response.setMessage("NO_USER_FOUND_WITH_THIS_ID");
            return response;
        }
        System.out.println(user);
        user.setUpdatedBy(user.getName());
        user.setName(updateUserRequest.getName());
        user.setRollNumber(updateUserRequest.getRollNumber());
        user.setUpdatedDate(Instant.now().getEpochSecond());

        userRepository.save(user);

        response.setMessage("User updated successfully");
        return response;
    }

    @Override
    public CommonAPIDataResponse deleteUserResponse(GetUserRequest getUserRequest) {
        User user = userRepository.findById(getUserRequest.getId());
        CommonAPIDataResponse response = new CommonAPIDataResponse();
        if (user==null){
            response.setMessage("NO_USER_FOUND");
            return response;
        }

        userRepository.delete(getUserRequest.getId());

        response.setMessage("User deleted successfully");
        return response;
    }

    @Override
    public GetAllUserResponse getAllUserResponse(GetAllUserRequest getAllUserRequest) {
        List<User> users = userRepository.findAllWithFilter(getAllUserRequest);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found");
        }

        List<UserData> userDataList = new ArrayList<>();
        for (User user : users) {
            userDataList.add(new UserData().setUserData(user));
        }

        GetAllUserResponse getAllUserResponse = new GetAllUserResponse();
        getAllUserResponse.setUserDataList(userDataList);
        return getAllUserResponse;
    }

    @Override
    public UploadFileResponse processUpload(UploadFileRequest uploadExcelFileRequest) {
        MultipartFile file = uploadExcelFileRequest.getFile();
        try {
            kafkaProducerSend.sendFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        GetAllUserRequest getAllUserRequest=new GetAllUserRequest();
//        List<User> userss = userRepository.findAllWithFilter(getAllUserRequest);
//
//        List<User> dataList = new ArrayList<>();
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            boolean isFirstRow = true;
//            for (Row row : sheet) {
//                if (isFirstRow) {
//                    isFirstRow = false;
//                    continue;
//                }
//                User data = new User();
//
//                Cell cellRollNumber = row.getCell(0);
//                Cell cellName = row.getCell(1);
//                Cell cellContact = row.getCell(2);
//                Cell cellAddress = row.getCell(3);
//
//                if (cellRollNumber == null) {
//                    continue; // Skip rows without roll number
//                }
//
//                String rollNumber;
//                if (cellRollNumber.getCellTypeEnum() == CellType.STRING) {
//                    rollNumber = cellRollNumber.getStringCellValue();
//                } else if (cellRollNumber.getCellTypeEnum() == CellType.NUMERIC) {
//                    rollNumber = String.valueOf((int) Math.round(cellRollNumber.getNumericCellValue()));
//                } else {
//                    continue; // Skip rows with invalid roll number format
//                }
//                data.setRollNumber(rollNumber);
//
//                if (cellName != null) {
//                    data.setName(cellName.getStringCellValue());
//                } else {
//                    data.setName("");
//                }
//
//                if (cellContact != null) {
//                    double numericValue = cellContact.getNumericCellValue();
//                    long longValue = new BigDecimal(numericValue).longValue();
//                    data.setContact(String.valueOf(longValue));
//                }
//
//                if (cellAddress != null) {
//                    data.setAddress(cellAddress.getStringCellValue());
//                } else {
//                    data.setAddress("");
//                }
//
//                data.setCreatedDate(Instant.now().getEpochSecond());
//
//                User exist = userRepository.findWithRollNumber(rollNumber);
//                if (exist != null && userss.isEmpty()) {
//                    continue;
//                } else if ((exist == null && userss.isEmpty())||(exist == null && !userss.isEmpty())) {
//                    dataList.add(data);
//                    userRepository.save(data);
//                } else if (exist != null && !userss.isEmpty()) {
//                    dataList.add(data);
//                    exist.setName(data.getName());
//                    exist.setContact(data.getContact());
//                    exist.setAddress(data.getAddress());
//                    exist.setUpdatedDate(Instant.now().getEpochSecond());
//                    userRepository.save(exist);
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Error processing Excel file: " + e.getMessage(), e);
//        }
//
//        List<String> rollNumberList = dataList.stream()
//                .map(User::getRollNumber)
//                .toList();
//        System.out.println("Roll numbers to keep: " + rollNumberList);
//        userRepository.deleteNotInList(rollNumberList);

        UploadFileResponse response = new UploadFileResponse();
        response.setMessage("Excel data send successfully");
        return response;
    }

}
