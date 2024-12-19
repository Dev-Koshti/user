package com.example.User.api.student.Dao;

import com.example.User.api.student.model.BankMaster;
import com.example.User.database.User;
import com.example.User.api.student.model.request.GetAllUserRequest;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User update(String id, Update updateDocument);
    User findById(String id);
    User delete(String id);
    List<User> findAllWithFilter(GetAllUserRequest getAllUserRequest);
User findWithRollNumber(String rollNumber);
void deleteNotInList(List<String> rollNumberList);

    List<BankMaster> getBankMasterListWithIntegerationType(String companyId, String searchKeyword, Integer skip, Integer limit, String sorting, Integer integerationType);
    long updateBankMaster(String id, Update updatedBankMaster);

}
