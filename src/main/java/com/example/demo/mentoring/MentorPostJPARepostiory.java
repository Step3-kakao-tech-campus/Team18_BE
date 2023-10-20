package com.example.demo.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MentorPostJPARepostiory extends JpaRepository<MentorPost, Integer> {

    @Query("select m from MentorPost m where m.writer.id = :writer and m.state = 'ACTIVE'")
    List<MentorPost> findAllByWriter(@Param("writer") int writer);

    Optional<MentorPost> findById(int id);

    @Query("select count(*) from MentorPost m where m.writer.id = :userId and m.state = 'ACTIVE'")
    int countContactByMentorId(int userId);

    @Query("select count(*) from MentorPost m where m.writer.id = :userId and m.state = 'DONE'")
    int countDoneByMentorId(int userId);

    @Query("SELECT m FROM MentorPost m INNER JOIN NotConnectedRegisterUser ncru ON m.id = ncru.mentorPost.id WHERE ncru.menteeUser.id = :menteeUserId")
    List<MentorPost> findAllByMenteeUserId(@Param("menteeUserId") int menteeUserId);
}
