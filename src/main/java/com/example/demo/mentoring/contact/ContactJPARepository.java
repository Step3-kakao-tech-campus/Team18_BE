package com.example.demo.mentoring.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactJPARepository extends JpaRepository<NotConnectedRegisterUser, Integer> {

    @Query("SELECT ncru FROM NotConnectedRegisterUser ncru WHERE ncru.mentorPost.id = :mentorPostId")
    List<NotConnectedRegisterUser> findAllByMentorPostId(@Param("mentorPostId") int mentorPostId);

    @Query("SELECT ncru FROM NotConnectedRegisterUser ncru WHERE ncru.mentorPost.id = :mentorPostId AND ncru.menteeUser.id = :menteeId")
    Optional<NotConnectedRegisterUser> findByMentorPostIdAndMenteeUserId(int mentorPostId, int menteeId);

    @Query("SELECT count(*) FROM NotConnectedRegisterUser ncru WHERE ncru.menteeUser.id = :userId")
    int countContactByMenteeId(int userId);
}
