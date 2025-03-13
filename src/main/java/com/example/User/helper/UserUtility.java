package com.example.User.helper;

import com.example.User.api.student.Dao.UserRepository;
import com.example.User.api.student.model.request.GetAllUserRequest;
import com.example.User.database.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtility {

    @Autowired
    private UserRepository userRepository;

    public void processExcelFile(String message) {


        // Decode Base64 string to byte array
        byte[] fileBytes = Base64.decodeBase64(message);

        // Create InputStream from byte array
        InputStream inputStream = new ByteArrayInputStream(fileBytes);


        List<User> userss = userRepository.findAllWithFilter(new GetAllUserRequest());

        List<User> dataList = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            boolean isFirstRow = true;
            for (Row row : sheet) {
                try {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                User data = new User();

                Cell cellRollNumber = row.getCell(0);
                Cell cellName = row.getCell(1);
                Cell cellContact = row.getCell(2);
                Cell cellAddress = row.getCell(3);

                if (cellRollNumber == null) {
                    continue; // Skip rows without roll number
                }

                String rollNumber;
                if (cellRollNumber.getCellTypeEnum() == CellType.STRING) {
                    rollNumber = cellRollNumber.getStringCellValue();
                } else if (cellRollNumber.getCellTypeEnum() == CellType.NUMERIC) {
                    rollNumber = String.valueOf((int) Math.round(cellRollNumber.getNumericCellValue()));
                } else {
                    continue; // Skip rows with invalid roll number format
                }
                data.setRollNumber(rollNumber);

                if (cellName != null) {
                    data.setName(cellName.getStringCellValue());
                } else {
                    data.setName("");
                }

                if (cellContact != null) {
                    double numericValue = cellContact.getNumericCellValue();
                    long longValue = new BigDecimal(numericValue).longValue();
                    data.setContact(String.valueOf(longValue));
                }

                if (cellAddress != null) {
                    data.setAddress(cellAddress.getStringCellValue());
                } else {
                    data.setAddress("");
                }

                data.setCreatedDate(Instant.now().getEpochSecond());

                User exist = userRepository.findWithRollNumber(rollNumber);
                if (exist != null && userss.isEmpty()) {
                    continue;
                } else if ((exist == null && userss.isEmpty())||(exist == null && !userss.isEmpty())) {
                    dataList.add(data);
                    userRepository.save(data);
                } else if (exist != null && !userss.isEmpty()) {
                    dataList.add(data);
                    exist.setName(data.getName());
                    exist.setContact(data.getContact());
                    exist.setAddress(data.getAddress());
                    exist.setUpdatedDate(Instant.now().getEpochSecond());
                    userRepository.save(exist);
                }
            }catch (Exception e){
                    System.err.println("Error processing row " + row.getRowNum());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file: " + e.getMessage(), e);
        }

        List<String> rollNumberList = dataList.stream()
                .map(User::getRollNumber)
                .toList();
//        System.out.println("Roll numbers to keep: " + rollNumberList);
        userRepository.deleteNotInList(rollNumberList);
    }

}
