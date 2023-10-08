package com.example.demo.mentoring;

import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<MentorPostResponse.MentorPostAllDTO> findAllMentorPost(int page) {
        Pageable pageable = PageRequest.of(page,5);

        Page<MentorPost> mentorPostList = mentorPostJPARepostiory.findAll(pageable);
        List<MentorPostResponse.MentorPostAllDTO> mentorPostDTOList = mentorPostList.stream().map(
                mentorPost -> {
                    List<UserInterest> writerInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
                    MentorPostResponse.MentorPostAllDTO.WriterDTO writerDTO = new MentorPostResponse.MentorPostAllDTO.WriterDTO(mentorPost.getWriter(), writerInterests);
                    return new MentorPostResponse.MentorPostAllDTO(mentorPost,writerDTO);
                }
        ).collect(Collectors.toList());
        return mentorPostDTOList;
    }
}
