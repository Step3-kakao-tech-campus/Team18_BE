package com.example.demo.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoInterestJPARepository  extends JpaRepository<VideoInterest, Integer> {
    @Query("select m from VideoInterest m where m.video.id = :video")
    List<VideoInterest> findVideoInterestByVideoId(@Param("video") int video);
}
