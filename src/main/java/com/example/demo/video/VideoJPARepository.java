package com.example.demo.video;

import com.example.demo.mentoring.MentorPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VideoJPARepository extends JpaRepository<Video, Integer> {
    Optional<Video> findById(int id);
}
