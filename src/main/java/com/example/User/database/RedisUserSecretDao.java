package com.example.User.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserSecretDao extends CrudRepository<UserAuth, String> {
}
