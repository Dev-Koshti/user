package com.example.User.database;

import com.example.User.api.student.model.request.SaveUserRequest;
import com.example.User.constant.Tables;
import com.example.User.utils.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Document(collection = Tables.User)
public class User extends CommonFieldModel{

    @Field("_id")
    @Builder.Default
    private String id= Utils.generateUUID();

    @Field("roll_number")
    private String rollNumber;

    @Field("name")
    private String name;

    @Field("contact")
    private String contact;

    @Field("address")
    private String address;

public User setUser(SaveUserRequest saveUserRequest){
    return User.builder()
            .name(saveUserRequest.getName())
            .rollNumber(saveUserRequest.getRollNumber())
            .contact(saveUserRequest.getContact())
            .address(saveUserRequest.getAddress())
            .createdBy(saveUserRequest.getName())
            .createdDate(Instant.now().getEpochSecond())
    .build();
}

//    @Builder
//    public User(String name, String rollNumber) {
//        super();
//        this.name = name;
//        this.rollNumber = rollNumber;
//    }

}
