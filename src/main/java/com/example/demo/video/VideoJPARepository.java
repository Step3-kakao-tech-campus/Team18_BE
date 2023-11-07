package com.example.demo.video;

import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.userInterest.UserInterest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoJPARepository extends JpaRepository<Video, Integer> {
    Optional<Video> findById(int id);

    @Query(value = "SELECT * FROM videos v " +
            "INNER JOIN video_interests vi ON v.id = vi.video_id " +
            "INNER JOIN interests i ON vi.interest_id = i.id " +
            "WHERE i.category IN :userInterests " +
            "ORDER BY RAND()", // MySQL에서 랜덤 정렬
            countQuery = "SELECT COUNT(*) FROM videos v " +
                    "INNER JOIN video_interests vi ON v.id = vi.video_id " +
                    "INNER JOIN interests i ON vi.interest_id = i.id " +
                    "WHERE i.category IN :userInterests", // 페이징을 위한 count 쿼리
            nativeQuery = true)
    Page<Video> findByVideoCategory(@Param("userInterests") List<String> userInterests, Pageable pageable);


    @Query(value = "select v from Video v inner join VideoInterest vi on v.id = vi.video.id where vi.interest.id = :categoryId")
    Page<Video> findByCategoryId(@Param("categoryId") int id, Pageable pageable);

    @Query(value = "select * from videos v where id != :videoId order by RAND() LIMIT 3", nativeQuery = true)
    List<Video> findThreeRandomVideo(@Param("videoId")int id);
}
