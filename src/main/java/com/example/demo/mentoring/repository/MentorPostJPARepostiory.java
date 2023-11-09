package com.example.demo.mentoring.repository;

import com.example.demo.mentoring.domain.MentoringBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MentorPostJPARepostiory extends JpaRepository<MentoringBoard, Integer> {

    @Query("select m from MentoringBoard m " +
            "where m.writer.id = :writer " +
            "and m.state = 'ACTIVE'")
    List<MentoringBoard> findAllByWriter(@Param("writer") int writer);

    @Query("select m from MentoringBoard m " +
            "where m.writer.id = :writer " +
            "and m.state = 'DONE'")
    List<MentoringBoard> findAllByWriterDone(@Param("writer") int writer);

    @Query("select m from MentoringBoard m where m.title like :keyword " +
            "and m.state = 'ACTIVE'")
    Page<MentoringBoard> findAllByTitleKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select m from MentoringBoard m " +
            "where m.writer.firstName " +
            "like :keyword or m.writer.firstName like :keyword " +
            "and m.state = 'ACTIVE'")
    Page<MentoringBoard> findAllByWriterKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select m from MentoringBoard m " +
            "inner join UserInterest ui ON m.writer.id = ui.user.id " +
            "where ui.interest.category like :keyword " +
            "and m.state = 'ACTIVE'")
    Page<MentoringBoard> findAllByInterestKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select m from MentoringBoard m " +
            "where m.state = 'ACTIVE'")
    Page<MentoringBoard> findAll(Pageable pageable);

    Optional<MentoringBoard> findById(int id);

    @Query("select count(*) from MentoringBoard m " +
            "where m.writer.id = :userId " +
            "and m.state = 'ACTIVE'")
    int countContactByMentorId(int userId);

    @Query("select count(*) from MentoringBoard m " +
            "where m.writer.id = :userId " +
            "and m.state = 'DONE'")
    int countDoneByMentorId(int userId);

}
