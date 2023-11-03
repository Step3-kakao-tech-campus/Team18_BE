package com.example.demo.video;

import com.example.demo.mentoring.MentorPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoJPARepository extends JpaRepository<Video, Integer> {
    Optional<Video> findById(int id);
}
