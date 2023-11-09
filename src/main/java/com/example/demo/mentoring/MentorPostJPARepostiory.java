package com.example.demo.mentoring;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MentorPostJPARepostiory extends JpaRepository<MentorPost, Integer> {

    @Query("select m from MentorPost m " +
            "where m.writer.id = :writer " +
            "and m.state = 'ACTIVE'")
    List<MentorPost> findAllByWriter(@Param("writer") int writer);

    @Query("select m from MentorPost m " +
            "where m.writer.id = :writer " +
            "and m.state = 'DONE'")
    List<MentorPost> findAllByWriterDone(@Param("writer") int writer);

    @Query("select m from MentorPost m where m.title like :keyword " +
            "and m.state = 'ACTIVE'")
    Page<MentorPost> findAllByTitleKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select m from MentorPost m " +
            "where m.writer.firstName " +
            "like :keyword or m.writer.firstName like :keyword " +
            "and m.state = 'ACTIVE'")
    Page<MentorPost> findAllByWriterKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select m from MentorPost m " +
            "inner join UserInterest ui ON m.writer.id = ui.user.id " +
            "where ui.interest.category like :keyword " +
            "and m.state = 'ACTIVE'")
    Page<MentorPost> findAllByInterestKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select m from MentorPost m " +
            "where m.state = 'ACTIVE'")
    Page<MentorPost> findAll(Pageable pageable);

    Optional<MentorPost> findById(int id);

    @Query("select count(*) from MentorPost m " +
            "where m.writer.id = :userId " +
            "and m.state = 'ACTIVE'")
    int countContactByMentorId(int userId);

    @Query("select count(*) from MentorPost m " +
            "where m.writer.id = :userId " +
            "and m.state = 'DONE'")
    int countDoneByMentorId(int userId);

    @Query("select m.createdAt from MentorPost m where m.writer.id = :userId order by m.createdAt desc")
    Page<LocalDateTime> createdRecentPost(int userId, Pageable pageable);
}
