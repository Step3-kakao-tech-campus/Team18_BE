package com.example.demo.video.repository;

import com.example.demo.user.domain.User;
import com.example.demo.video.domain.Video;
import com.example.demo.video.domain.VideoHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoHistoryJPARepository extends JpaRepository<VideoHistory, Integer> {
    @Query("select vh.video from VideoHistory vh where vh.user.id = :id order by vh.updatedAt desc")
    Page<Video> findAllByUserId(int id, Pageable pageable);

}
