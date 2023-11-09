package com.example.demo.user.repository;

import com.example.demo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(@Param("email") String email);
}
