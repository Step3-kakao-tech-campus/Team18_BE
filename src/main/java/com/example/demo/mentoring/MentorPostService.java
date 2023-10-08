package com.example.demo.mentoring;

import com.example.demo.account.Account;
import com.example.demo.account.userInterest.UserInterest;
import com.example.demo.account.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class MentorPostService {
    private final MentorPostJPARepostiory mentorPostJPARepostiory;
    private final UserInterestJPARepository userInterestJPARepository;

    //mentorPost생성
    @Transactional
    public void createMentorPost(MentorPostRequest.CreateDTO createDTO, Account writer) {
        mentorPostJPARepostiory.save(createDTO.toEntity(writer));
    }


    public List<MentorPostResponse.MentorPostDTO> findAllMentorPost() {
        List<MentorPost> mentorPostList = mentorPostJPARepostiory.findAll();
        List<MentorPostResponse.MentorPostDTO> mentorPostDTOList = mentorPostList.stream().map(
                mentorPost -> {
                    List<UserInterest> writerInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
                    MentorPostResponse.MentorPostDTO.WriterDTO writerDTO = new MentorPostResponse.MentorPostDTO.WriterDTO(mentorPost.getWriter(), writerInterests);
                    return new MentorPostResponse.MentorPostDTO(mentorPost,writerDTO);
                }
        ).collect(Collectors.toList());
        return mentorPostDTOList;
    }
}
