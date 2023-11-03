package com.example.demo.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubtitleJPARepository extends JpaRepository<Subtitle, Integer> {
    @Query("select m from Subtitle m where m.video.id = :video")
    List<Subtitle> findSubtitleByVideoId(@Param("video") int video);
}
