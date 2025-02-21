package com.example.User.helper;

import com.example.User.api.student.Dao.UserRepository;
import com.example.User.api.student.model.request.GetAllUserRequest;
import com.example.User.constant.AppConstants;
import com.example.User.constant.HeaderConstants;
import com.example.User.database.User;
import com.example.User.database.UserAuth;
import com.example.User.kafka.producer.KafkaProducerSend;
import com.example.User.utils.Logger;
import com.example.User.utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

@Component
public class UserUtility {

    @Autowired
    private UserRepository userRepository;

    @Value("${data.dog.log.source.name}")
    private static String dataDogSourceName;

    @Autowired
    private KafkaProducerSend kafkaProducerSend;

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

    public static JsonNode sendUnAuthorized() {
        Map<String, Object> mapBodyException = new HashMap<>();
        mapBodyException.put("status", "error");
        mapBodyException.put("code", HttpStatus.UNAUTHORIZED.value());
        mapBodyException.put("message", HttpStatus.UNAUTHORIZED.name());
        return new ObjectMapper().convertValue(mapBodyException, JsonNode.class);
    }
    public static <T> boolean checkSumVerify(T requestObject, String checkSum, int httpMethod) {
        try {
            Field[] fields = requestObject.getClass().getDeclaredFields();
            Field[] superClassFields = requestObject.getClass().getSuperclass().getDeclaredFields();

            List<String> fieldNameList = new ArrayList<>();
            Map<String, Object> fieldValue = new HashMap<>();

            switch (httpMethod) {
                case AppConstants.HTTPMETHODS.GET_ALL:
                case AppConstants.HTTPMETHODS.DELETE:
                case AppConstants.HTTPMETHODS.GET:
                case AppConstants.HTTPMETHODS.POST:
                    Arrays.stream(fields).parallel().forEach(field -> {
                        field.setAccessible(true);
                        try {
                            if (field.get(requestObject) != null) {
                                if (field.get(requestObject) instanceof String
                                        || field.get(requestObject) instanceof Boolean
                                        || field.get(requestObject) instanceof Long
                                        || field.get(requestObject) instanceof Integer
                                        || field.get(requestObject) instanceof Double) {
                                    processAccordingToGeneralData(field, requestObject, httpMethod, fieldValue, fieldNameList, false);
                                } else if (field.get(requestObject) instanceof Map) {
                                    processAccordingToSingleMap(field, requestObject, fieldValue, fieldNameList, false);
                                } else if (field.get(requestObject) instanceof List) {
                                    List<T> listOfObjects = (List<T>) field.get(requestObject);
                                    for (T singleObject : listOfObjects) {
                                        if (singleObject instanceof Map) {
                                            processAccordingToSingleMap(field, singleObject, fieldValue, fieldNameList, true);
                                        } else if (singleObject instanceof String
                                                || field.get(requestObject) instanceof Boolean
                                                || field.get(requestObject) instanceof Long
                                                || field.get(requestObject) instanceof Integer
                                                || field.get(requestObject) instanceof Double) {
                                            processAccordingToGeneralData(field, requestObject, httpMethod, fieldValue, fieldNameList, true);
                                        }
                                    }
                                } else {
                                    Field[] declaredFields = field.get(requestObject).getClass().getDeclaredFields();
                                    fieldNameList.add(field.getName());
                                    StringBuilder builder = new StringBuilder();
                                    for (Field staticClassField : declaredFields) {
                                        staticClassField.setAccessible(true);
                                        if (staticClassField.get(field.get(requestObject)) != null) {
                                            builder.append(staticClassField.getName()).append(staticClassField.get(field.get(requestObject)));
                                        }
                                    }
                                    fieldValue.put(field.getName(), builder.toString().replaceAll("[{}\\\":\\\\s,]", ""));
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
                case AppConstants.HTTPMETHODS.PUT:
                    Arrays.stream(fields).parallel().forEach(field -> {
                        field.setAccessible(true);
                        try {
                            if (field.get(requestObject) != null && field.get(requestObject) instanceof Map) {
                                processAccordingToSingleMap(field, requestObject, fieldValue, fieldNameList, false);
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
            }
            Arrays.stream(superClassFields).parallel().forEach(superClassField -> {
                superClassField.setAccessible(true);
                try {
                    if (superClassField.get(requestObject) != null) {
                        if (superClassField.get(requestObject) instanceof String
                                || superClassField.get(requestObject) instanceof Boolean
                                || superClassField.get(requestObject) instanceof Long
                                || superClassField.get(requestObject) instanceof Integer
                                || superClassField.get(requestObject) instanceof Double) {
                            processAccordingToGeneralData(superClassField, requestObject, httpMethod, fieldValue, fieldNameList, false);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            Collections.sort(fieldNameList);
            StringBuilder payloadBuilder = new StringBuilder();
            for (String filedName : fieldNameList) {
                payloadBuilder.append(filedName).append(fieldValue.get(filedName));
            }

//            return MessageDigest.isEqual(Objects.requireNonNull(generateHmac256(new String(payloadBuilder), "secret".getBytes())).getBytes(StandardCharsets.UTF_8)
//                    , checkSum.getBytes(StandardCharsets.UTF_8));
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    private static <T> void processAccordingToSingleMap(Field field, T requestObject
            , Map<String, Object> fieldValue, List<String> fieldNameList, boolean isFromList) throws IllegalAccessException {
        Map<String, Object> stringObjectMap;
        if (requestObject instanceof LinkedHashMap) {
            stringObjectMap = ((LinkedHashMap) requestObject);
        } else {
            stringObjectMap = (LinkedHashMap<String, Object>) field.get(requestObject);
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
            builder.append(entry.getKey()).append(entry.getValue());
        }
        if (isFromList) {
            if (fieldNameList.contains(field.getName())) {
                StringBuilder previousValue = new StringBuilder(String.valueOf(fieldValue.get(field.getName())));
                previousValue.append(builder.toString().replaceAll("[{}\\[\\]=\\s,]", ""));
                fieldValue.put(field.getName(), previousValue.toString());
            } else {
                fieldNameList.add(field.getName());
                fieldValue.put(field.getName(), builder.toString().replaceAll("[{}\\[\\]=\\s,]", ""));
            }
        } else {
            fieldNameList.add(field.getName());
            fieldValue.put(field.getName(), builder.toString().replaceAll("[{}\\[\\]=\\s,]", ""));
        }
    }


    private static <T> void processAccordingToGeneralData(Field field, T requestObject, int httpMethod
            , Map<String, Object> fieldValue, List<String> fieldNameList, boolean isFromList) throws IllegalAccessException {
        if (field.get(requestObject) != null) {
            if (isFromList) {
                if (!fieldNameList.contains(field.getName())) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(httpMethod == AppConstants.HTTPMETHODS.GET_ALL ?
                            URLDecoder.decode(String.valueOf(field.get(requestObject)), StandardCharsets.UTF_8) : field.get(requestObject));
                    fieldValue.put(field.getName(), builder.toString().replaceAll("[{}\\[\\]=\\s,]", ""));
                    fieldNameList.add(field.getName());
                }
            } else {

                fieldValue.put(field.getName(), httpMethod == AppConstants.HTTPMETHODS.GET_ALL ?
                        URLDecoder.decode(String.valueOf(field.get(requestObject)).replaceAll("[{}\\[\\]=\\s,]", ""), StandardCharsets.UTF_8).replaceAll("[\\s\\[\\]{}\":,]", "") : field.get(requestObject));
                fieldNameList.add(field.getName());
            }
        }
    }


    public boolean verifyCheckSum(String payload, HttpServletRequest request, int method, String checkSum) {

        Enumeration<String> headerNames = request.getHeaderNames();
        ArrayList<String> listOfHeader = Collections.list(headerNames);
        StringBuilder builder = new StringBuilder();
        List<String> finalSortPath = new ArrayList<>();
        Map<String, Object> stringStringMap;
        try {
            switch (method) {
                case AppConstants.HTTPMETHODS.PUT:
                case AppConstants.HTTPMETHODS.POST:
                    if (!StringUtils.isEmpty(payload) && !payload.equalsIgnoreCase("null")) {
                        stringStringMap = new ObjectMapper().readValue(payload, new TypeReference<Map<String, Object>>() {
                        });
                    } else {
                        stringStringMap = new HashMap<>();
                    }
                    listOfHeader.stream().parallel().forEach(header -> {
                        if (header.equalsIgnoreCase(HeaderConstants.REQUEST_ID)) {
                            stringStringMap.put("request_id", request.getHeader(header));
                        } else if (header.equalsIgnoreCase(HeaderConstants.COMPANY_ID)) {
                            stringStringMap.put("company_id", request.getHeader(header));
                        }
                    });
                    stringStringMap.entrySet().parallelStream().forEach(entry -> finalSortPath.add(entry.getKey()));

                    Collections.sort(finalSortPath);
                    for (String name : finalSortPath) {
                        builder.append(name).append(String.valueOf(stringStringMap.get(name)).replaceAll("[{}\\[\\]=\\s,]", ""));
                    }
                    break;

                case AppConstants.HTTPMETHODS.GET_ALL:
                    stringStringMap = new HashMap<>();

                    if (!StringUtils.isEmpty(payload)) {
                        String[] queries = payload.split("&");
                        List<String> listOfQueries = Arrays.asList(queries);

                        listOfQueries.stream().parallel().forEach(list -> {
                            String[] valueAndKey = list.split("=");
                            stringStringMap.put(valueAndKey[0], valueAndKey[1]);
                            finalSortPath.add(valueAndKey[0]);
                        });
                    }

                    setHeaderListForGetAndDelete(listOfHeader, request, stringStringMap, finalSortPath);

                    Collections.sort(finalSortPath);

                    for (String queryKey : finalSortPath) {
                        builder.append(queryKey).append(URLDecoder.decode(String.valueOf(stringStringMap.get(queryKey)).replaceAll("[\":{}\\[\\]=]", ""), StandardCharsets.UTF_8));
                    }
                    break;

                case AppConstants.HTTPMETHODS.GET:
                case AppConstants.HTTPMETHODS.DELETE:
                    stringStringMap = new HashMap<>();

                    setHeaderListForGetAndDelete(listOfHeader, request, stringStringMap, finalSortPath);

                    finalSortPath.add("id");
                    stringStringMap.put("id", payload);
                    Collections.sort(finalSortPath);

                    for (String key : finalSortPath) {
                        builder.append(key).append(URLDecoder.decode(String.valueOf(stringStringMap.get(key)), StandardCharsets.UTF_8));
                    }
                    break;

                default:
                    return true;
            }
            return !MessageDigest.isEqual(Objects.requireNonNull(generateHmac256(new String(builder).replaceAll("[\"{}\\s:]", "")
                    , "secret".getBytes())).getBytes(StandardCharsets.UTF_8), checkSum.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            Logger.error(ExceptionUtils.getStackTrace(e));
            return true;
        }
    }

    private void setHeaderListForGetAndDelete(List<String> listOfHeader, HttpServletRequest request
            , Map<String, Object> stringObjectMap, List<String> finalSortPath) {
        listOfHeader.stream().parallel().forEach(header -> {
            if (header.equalsIgnoreCase(HeaderConstants.REQUEST_ID)) {
                stringObjectMap.put("request_id", request.getHeader(header));
                finalSortPath.add("request_id");
            } else if (header.equalsIgnoreCase(HeaderConstants.COMPANY_ID)) {
                stringObjectMap.put("company_id", request.getHeader(header));
                finalSortPath.add("company_id");
            }
        });
    }

    private static String generateHmac256(String message, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] bytes = hmac(key, message.getBytes());
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] hmac(byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(AppConstants.ALGORITHM.HMACSHA256);
        mac.init(new SecretKeySpec(key, AppConstants.ALGORITHM.HMACSHA256));
        return mac.doFinal(message);
    }

    public void produceCommonAPILogs(Map<String, Object> stringObjectHashMap, String requestId) {
        ObjectMapper objectMapper = new ObjectMapper();
        stringObjectHashMap.put("service_name", AppConstants.DATA_DOG_SERVICE_NAME);
        stringObjectHashMap.put("source_name", dataDogSourceName);


        JsonNode jsonNode = objectMapper.valueToTree(stringObjectHashMap);

        kafkaProducerSend.sendCommonAPILogMessage(jsonNode.toString(), requestId);
    }

    public void sendRemoveExpiredTokenDataToProducer(String encryptedToken, String userId
            , String requestId, UserAuth userAuth) {
        Map<String, Object> kafkaMap = new HashMap<>();
        kafkaMap.put(AppConstants.AUTH_TOKEN_KEY, encryptedToken);
        kafkaMap.put(HeaderConstants.TOKEN_USER_ID, userId);
        kafkaMap.put("user_secret_data", userAuth);
        kafkaProducerSend.removeExpiredTokenFromDataBase(Utils.convertHashMapToJsonString(kafkaMap), requestId);
    }
}
