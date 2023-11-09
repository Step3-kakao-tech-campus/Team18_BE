package com.example.demo.mentoring.domain;

import com.example.demo.mentoring.repository.MentorPostJPARepostiory;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
public enum MentorSearchCategory {
    NULL((keyword, pageable, repo) -> repo.findAll(pageable)),
    TITLE((keyword, pageable, repo) -> repo.findAllByTitleKeyword("%" + keyword + "%", pageable)),
    WRITER((keyword, pageable, repo) -> repo.findAllByWriterKeyword("%" + keyword + "%", pageable)),
    INTEREST((keyword, pageable, repo) -> repo.findAllByInterestKeyword("%" + keyword + "%", pageable));

    private final TriFunction<String, Pageable, MentorPostJPARepostiory, Page<MentoringBoard>> function;

    MentorSearchCategory(TriFunction<String, Pageable, MentorPostJPARepostiory, Page<MentoringBoard>> function) {
        this.function = function;
    }

    public Page<MentoringBoard> execute(String keyword, Pageable pageable, MentorPostJPARepostiory repository) {
        return function.apply(keyword, pageable, repository);
    }
}