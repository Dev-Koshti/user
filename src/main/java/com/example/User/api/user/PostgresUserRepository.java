package com.example.User.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresUserRepository extends JpaRepository<PostgresUser, Long> {
}
