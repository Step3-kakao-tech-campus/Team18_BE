package com.example.demo.video;

import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.userInterest.UserInterest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoJPARepository extends JpaRepository<Video, Integer> {
    Optional<Video> findById(int id);

    @Query("select v from Video v join fetch VideoInterest vi on v.id = vi.video.id where vi.interest.category in :userInterest")
    Page<Video> findByVideoCategory(List<String> userInterests, Pageable pageable);
}
