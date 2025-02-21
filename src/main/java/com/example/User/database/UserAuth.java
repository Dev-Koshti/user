package com.example.User.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("user_auth")
public class UserAuth implements Serializable {
    @Id
    private String user_id;
    // save field by _ dont use camel case.
    private String secret_key;
    private List<String> user_token;
    private Long created_date;
    private Long updated_date;
    private Integer number_of_sessions_active;
}
