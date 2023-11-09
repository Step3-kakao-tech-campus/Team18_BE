package com.example.demo.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoHistoryJPARepository extends JpaRepository<VideoHistory, Integer> {
    @Query("select vh from VideoHistory vh where vh.user.id = :userid order by vh.createdAt desc")
    Page<VideoHistory> findHistoryVideo(@Param("userid")int userid, Pageable pageable);

    @Query("select vh from VideoHistory vh where vh.video.id = :vid and vh.user.id = :userid ")
    Optional<VideoHistory> findByVideoId(@Param("userid")int userid, @Param("vid")int vid);
}
