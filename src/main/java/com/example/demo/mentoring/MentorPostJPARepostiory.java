package com.example.demo.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MentorPostJPARepostiory extends JpaRepository<MentorPost, Integer> {

    @Query("select m from MentorPost m where m.writer.id = :writer")
    List<MentorPost> findAllByWriter(@Param("writer") int writer);

    MentorPost findById(int id);
}