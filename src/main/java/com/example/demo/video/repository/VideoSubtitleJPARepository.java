package com.example.demo.video.repository;

import com.example.demo.video.domain.VideoSubtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoSubtitleJPARepository extends JpaRepository<VideoSubtitle, Integer> {
    @Query("select m from VideoSubtitle m where m.video.id = :video")
    List<VideoSubtitle> findSubtitleByVideoId(@Param("video") int video);
}
