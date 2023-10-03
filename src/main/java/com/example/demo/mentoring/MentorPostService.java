package com.example.demo.mentoring;

import com.example.demo.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class MentorPostService {
    private final MentorPostJPARepostiory mentorPostJPARepostiory;

    //mentorPost생성
    @Transactional
    public void createMentorPost(MentorPostRequest.CreateDTO createDTO, Account writer) {
        mentorPostJPARepostiory.save(createDTO.toEntity(writer));
    }
}
