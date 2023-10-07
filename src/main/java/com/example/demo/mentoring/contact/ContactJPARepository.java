package com.example.demo.mentoring.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactJPARepository extends JpaRepository<NotConnectedRegisterUser, Integer> {

    @Query("SELECT ncru FROM NotConnectedRegisterUser ncru WHERE ncru.mentorPost.id = :mentorPostId")
    List<NotConnectedRegisterUser> findAllByMentorPostId(@Param("mentorPostId") int mentorPostId);

}
