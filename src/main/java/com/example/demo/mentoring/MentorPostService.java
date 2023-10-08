package com.example.demo.mentoring;

import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
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
    public void createMentorPost(MentorPostRequest.CreateDTO createDTO, User writer) {
        mentorPostJPARepostiory.save(createDTO.toEntity(writer));
    }

   /* 1. mentorPostList를 조회
    2. 각 List당 writer별 writerInterests를 조회
    3. MentorPostDTO 생성*/
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
