package com.example.User.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PostgresUserController {

    @Autowired
    private PostgresUserService userService;

    @PostMapping
    public PostgresUser createUser(@RequestBody PostgresUser user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<PostgresUser> getAll(){
        return userService.getAll();
    }
}
