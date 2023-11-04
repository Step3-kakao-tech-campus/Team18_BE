package com.example.demo.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoHistoryJPARepository extends JpaRepository<VideoHistory, Integer> {
    @Query("select vh from VideoHistory vh where vh.user.id = :id order by vh.createdAt desc")
    Page<VideoHistory> findHistoryVideo(int id, Pageable pageable);
}
