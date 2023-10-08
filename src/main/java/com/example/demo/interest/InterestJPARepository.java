package com.example.demo.interest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestJPARepository extends JpaRepository<Interest, Integer> {
    Optional<Interest> findByCategory(String category);
}
