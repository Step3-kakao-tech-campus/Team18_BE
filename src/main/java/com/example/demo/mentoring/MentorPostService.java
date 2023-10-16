package com.example.demo.mentoring;

import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.mentoring.contact.ContactJPARepository;
import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class MentorPostService {
    private final MentorPostJPARepostiory mentorPostJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final ContactJPARepository contactJPARepository;

    //mentorPost생성
    @Transactional
    public void createMentorPost(MentorPostRequest.CreateDTO createDTO, User writer) {
        MentorPost mentorPost = new MentorPost( writer, createDTO.getTitle(), createDTO.getContent());
        mentorPostJPARepository.save(mentorPost);
    }

   /* 1. mentorPostList를 조회
    2. 각 List당 writer별 writerInterests를 조회
    3. MentorPostDTO 생성*/
    public List<MentorPostResponse.MentorPostAllDTO> findAllMentorPost(int page) {
        Pageable pageable = PageRequest.of(page,5);

        Page<MentorPost> pageContent = mentorPostJPARepository.findAll(pageable);
        //List<MentorPost> mentorPostList = mentorPostJPARepostiory.findAll();
        List<MentorPostResponse.MentorPostAllDTO> mentorPostDTOList = pageContent.getContent().stream().map(
                mentorPost -> {
                    List<UserInterest> writerInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
                    return new MentorPostResponse.MentorPostAllDTO(mentorPost,writerInterests);
                }
        ).collect(Collectors.toList());
        return mentorPostDTOList;
    }

    public MentorPostResponse.MentorPostDTO findMentorPost(int id){
        MentorPost mentorPost = mentorPostJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 게시글이 존재하지 않습니다."));

        //writer 데이터
        User mentor = mentorPost.getWriter();
        //mentee들 데이터
        List<NotConnectedRegisterUser> menteeList = contactJPARepository.findAllByMentorPostId(id);
        //writer Interest데이터
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentor.getId());
        //mentee들 Interest데이터
        List<UserInterest> menteeInterests = menteeList.stream()
                .flatMap(mentee -> userInterestJPARepository.findAllById(mentee.getMenteeUser().getId()).stream())
                .collect(Collectors.toList());

        MentorPostResponse.MentorPostDTO mentorPostDTO = new MentorPostResponse.MentorPostDTO(mentorPost, mentorInterests, menteeList, menteeInterests);

        return mentorPostDTO;
    }
    @Transactional
    public void updateMentorPost(MentorPostRequest.CreateDTO createDTO, int id)
    {
        MentorPost mentorPost = mentorPostJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 게시글이 존재하지 않습니다."));

        mentorPost.update(createDTO.getTitle(), createDTO.getContent());

    }

    public void deleteMentorPost(int id) {
        mentorPostJPARepository.deleteById(id);
    }

    //생성 시간까지 조회하는 test service 코드 입니다
    public List<MentorPostResponse.MentorPostAllWithTimeStampDTO> findAllMentorPostWithTimeStamp() {
        List<MentorPost> pageContent = mentorPostJPARepository.findAll();
        //List<MentorPost> mentorPostList = mentorPostJPARepostiory.findAll();
        List<MentorPostResponse.MentorPostAllWithTimeStampDTO> mentorPostDTOList = pageContent.stream().map(
                mentorPost -> {
                    List<UserInterest> writerInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
                    return new MentorPostResponse.MentorPostAllWithTimeStampDTO(mentorPost,writerInterests);
                }
        ).collect(Collectors.toList());
        return mentorPostDTOList;
    }

    public void changeMentorPostStatus(MentorPostRequest.StateDTO stateDTO, int id)
    {
        MentorPost mentorPost = mentorPostJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 게시글이 존재하지 않습니다."));

        mentorPost.changeStatus(stateDTO.getStateEnum());

    }
}


