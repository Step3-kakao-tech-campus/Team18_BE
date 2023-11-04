package com.example.demo.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoInterestJPARepository  extends JpaRepository<VideoInterest, Integer> {
    @Query("select vi from VideoInterest vi where vi.video.id = :video")
    VideoInterest findVideoInterestByVideoId(@Param("video") int video);
}
