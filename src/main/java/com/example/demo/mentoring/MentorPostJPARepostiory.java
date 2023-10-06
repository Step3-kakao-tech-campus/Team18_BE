package com.example.demo.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorPostJPARepostiory extends JpaRepository<MentorPost, Integer> {
    List<MentorPost> findAllByWriter(int writer);

    MentorPost findById(int id);
}