package com.example.demo.user.repository;

import com.example.demo.user.domain.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInterestJPARepository extends JpaRepository<UserInterest, Integer> {

    @Query("select u from UserInterest u where u.user.id = :id")
    List<UserInterest> findAllById(@Param("id") int id);

    @Query("select ui from UserInterest ui where ui.user.id = :user_id and ui.interest.category = :category")
    void deleteByUserAndInterest(@Param("user_id") int user_id, @Param("category") String category);
}
