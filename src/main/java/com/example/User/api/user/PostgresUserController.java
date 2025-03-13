package com.example.User.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class PostgresUserController {

    @Autowired
    private PostgresUserService userService;

    @PostMapping
    public PostgresUser createUser(@RequestBody PostgresUser user) {
        return userService.saveUser(user);
    }
}
