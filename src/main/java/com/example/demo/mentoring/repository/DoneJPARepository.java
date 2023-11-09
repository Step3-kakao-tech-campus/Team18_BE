package com.example.demo.mentoring.repository;

import com.example.demo.mentoring.domain.ConnectedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoneJPARepository extends JpaRepository<ConnectedUser, Integer> {

    @Query("SELECT count(*) FROM ConnectedUser cu WHERE cu.menteeUser.id = :userId")
    int countDoneByMenteeId(int userId);

    @Query("SELECT cu FROM ConnectedUser cu WHERE cu.menteeUser.id = :id")
    List<ConnectedUser> findAllByMenteeId(int id);

    @Query("SELECT cu FROM ConnectedUser cu WHERE cu.mentorPost.id = :id")
    List<ConnectedUser> findAllByMentorPostId(int id);

    @Query("SELECT cu FROM ConnectedUser cu WHERE cu.mentorPost.writer.id = :id")
    ConnectedUser findByMentorId(int id);
}
