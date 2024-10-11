package com.example.User.api.student.model.response;

import com.example.User.database.CommonFieldModel;
import com.example.User.database.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserData extends CommonFieldModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("contact")
    private String contact;

    public UserData setUserData(User user){
        UserData userData=new UserData();
        userData.setId(String.valueOf(user.getId()));
        userData.setName(user.getName());
        userData.setRollNumber(user.getRollNumber());
        userData.setContact(user.getContact());
        userData.setAddress(user.getAddress());
        userData.setCreatedBy(user.getCreatedBy());
        userData.setUpdatedBy(user.getUpdatedBy());
        userData.setIsActive(user.getIsActive());
        userData.setCreatedDate(user.getCreatedDate());
        userData.setUpdatedDate(user.getUpdatedDate());
        return userData;
    }


}
