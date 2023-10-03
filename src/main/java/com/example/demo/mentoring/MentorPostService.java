package com.example.demo.mentoring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class MentorPostService {
    private final MentorPostJPARepostiory mentorPostJPARepostiory;

    @Transactional
    public void createPost(MentorPostRequest.CreateDTO createDTO) {
        mentorPostJPARepostiory.save(createDTO.toEntity());
    }
}
