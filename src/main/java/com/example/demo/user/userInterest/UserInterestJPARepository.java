package com.example.demo.user.userInterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserInterestJPARepository extends JpaRepository<UserInterest, Integer> {

    @Query("select u from UserInterest u where u.user.id = :id")
    List<UserInterest> findAllById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("delete from UserInterest ui where ui.user.id = :userId and ui.interest.id = :interestId")
    void deleteByUserAndInterest(@Param("userId") int userId, @Param("interestId") int interestId);
}
