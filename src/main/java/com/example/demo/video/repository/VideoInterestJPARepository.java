package com.example.demo.video.repository;

import com.example.demo.video.domain.Video;
import com.example.demo.video.domain.VideoInterest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoInterestJPARepository  extends JpaRepository<VideoInterest, Integer> {
    @Query("select vi from VideoInterest vi where vi.video.id = :video")
    VideoInterest findVideoInterestByVideoId(@Param("video") int video);

    @Query(value = "select vi.video from VideoInterest vi where vi.interest.category = :category")
    Page<Video> findByCategory(@Param("category") String category, Pageable pageable);
}
