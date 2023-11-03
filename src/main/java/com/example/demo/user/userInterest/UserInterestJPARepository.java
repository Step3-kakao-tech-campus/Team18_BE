package com.example.demo.user.userInterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInterestJPARepository extends JpaRepository<UserInterest, Integer> {

    @Query("select u from UserInterest u join fetch u.interest i where u.user.id = :id")
    List<UserInterest> findAllById(@Param("id") int id);

    @Query("select ui from UserInterest ui where ui.user.id = :id")
    void deleteAllByUserId(@Param("id") int id);
}
