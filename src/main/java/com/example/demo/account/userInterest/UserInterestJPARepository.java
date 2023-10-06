package com.example.demo.account.userInterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInterestJPARepository extends JpaRepository<UserInterest, Integer> {
    @Query("select u from UserInterest u join fetch Interest i where u.user.id = :id")
    List<UserInterest> findAllById(int id);

}
