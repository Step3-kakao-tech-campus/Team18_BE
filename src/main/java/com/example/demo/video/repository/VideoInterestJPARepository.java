package com.example.demo.video.repository;

import com.example.demo.video.domain.Video;
import com.example.demo.video.domain.VideoInterest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoInterestJPARepository  extends JpaRepository<VideoInterest, Integer> {
//    @Query("select vi from VideoInterest vi where vi.video.id = :id")
//    VideoInterest findVideoInterestByVideoId(@Param("id") int id);

    @Query(value = "select vi.video from VideoInterest vi where vi.interest.category = :category")
    Page<Video> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("select vi from VideoInterest vi where vi.video.id = :id")
    List<VideoInterest> findVideoInterestByVideoId(@Param("id") int id);
}
