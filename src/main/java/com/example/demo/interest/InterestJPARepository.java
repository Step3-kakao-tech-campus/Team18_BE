package com.example.demo.interest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface InterestJPARepository extends JpaRepository<Interest, Integer> {
    Optional<Interest> findByCategory(@Param("category") String category);
}
