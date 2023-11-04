package com.example.demo.refreshToken;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenJPARepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByUser(User user);

    @Query("select r from RefreshToken r join fetch r.user where r.user.id = :id")
    Optional<RefreshToken> findByUserId(int id);
}
