package com.example.User.database;


import java.util.List;

public interface UserAuthTokenQueryDao {

    UserAuthToken findById(String id);

    void save(UserAuthToken userAuthToken);

    List<UserAuthToken> getAll();
}
