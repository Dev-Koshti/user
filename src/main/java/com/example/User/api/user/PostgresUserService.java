package com.example.User.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostgresUserService {

    @Autowired
    private PostgresUserRepository userRepository;

    public PostgresUser saveUser(PostgresUser user) {
        return userRepository.save(user);
    }
}
