package com.example.demo.mentoring.service;

import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception500;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.mentoring.domain.MentoringBoard;
import com.example.demo.mentoring.repository.ContactJPARepository;
import com.example.demo.mentoring.domain.NotConnectedRegisterUser;
import com.example.demo.mentoring.domain.MentorSearchCategory;
import com.example.demo.mentoring.dto.MentorPostRequest;
import com.example.demo.mentoring.dto.MentorPostResponse;
import com.example.demo.mentoring.repository.MentorPostJPARepostiory;
import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserInterest;
import com.example.demo.user.repository.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
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
    public void createMentorPost(MentorPostRequest.CreateMentorPostDTO createMentorPostDTO, User writer) {
        isMentor(writer);

        MentoringBoard mentoringBoard = new MentoringBoard( writer, createMentorPostDTO.getTitle(), createMentorPostDTO.getContent());

        try {
            mentorPostJPARepository.save(mentoringBoard);
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

   /* 1. mentorPostList를 조회
    2. 각 List당 writer별 writerInterests를 조회
    3. MentorPostDTO 생성*/
    public List<MentorPostResponse.MentorPostAllDTO> findAllMentorPost(String search, String keyword, int page) {
        Pageable pageable = PageRequest.of(page,5);

        MentorSearchCategory mentorSearchCategory = MentorSearchCategory.valueOf(search.toUpperCase(Locale.ROOT));

        Page<MentoringBoard> result = mentorSearchCategory.execute(keyword, pageable, mentorPostJPARepository);
        List<MentorPostResponse.MentorPostAllDTO> mentorPostAllDTOS = result.stream().map(mentorPost -> {
            List<UserInterest> writerInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
            return new MentorPostResponse.MentorPostAllDTO(mentorPost, writerInterests);
        }).collect(Collectors.toList());

        return mentorPostAllDTOS;
    }

    public MentorPostResponse.MentorPostDTO findMentorPost(int id){
        MentoringBoard mentoringBoard = mentorPostJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 글이 존재하지 않습니다.\n" + "id : " + id));

        //writer 데이터
        //writer를 제외하고는 다 null 가능
        User mentor = mentoringBoard.getWriter();
        //mentee들 데이터
        List<NotConnectedRegisterUser> menteeList = contactJPARepository.findAllByMentorPostId(id);
        //writer Interest데이터
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentor.getId());
        //mentee들 Interest데이터
        List<UserInterest> menteeInterests = menteeList.stream()
                .flatMap(mentee -> userInterestJPARepository.findAllById(mentee.getMenteeUser().getId()).stream())
                .collect(Collectors.toList());

        MentorPostResponse.MentorPostDTO mentorPostDTO = new MentorPostResponse.MentorPostDTO(mentoringBoard, mentorInterests, menteeList, menteeInterests);

        return mentorPostDTO;
    }
    @Transactional
    public void updateMentorPost(MentorPostRequest.CreateMentorPostDTO createMentorPostDTO, int id, User writer) {
        isMentor(writer);

        MentoringBoard mentoringBoard = mentorPostJPARepository.findById(id).
                orElseThrow(() -> new Exception404("해당 글이 존재하지 않습니다."));

        try {
            mentoringBoard.update(createMentorPostDTO.getTitle(), createMentorPostDTO.getContent());
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    public void deleteMentorPost(int id, User writer) {
        isMentor(writer);

        try {
            mentorPostJPARepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    //생성 시간까지 조회하는 test service 코드 입니다
    public List<MentorPostResponse.MentorPostAllWithTimeStampDTO> findAllMentorPostWithTimeStamp() {
        List<MentoringBoard> pageContent = mentorPostJPARepository.findAll();
        //List<MentorPost> mentorPostList = mentorPostJPARepostiory.findAll();
        List<MentorPostResponse.MentorPostAllWithTimeStampDTO> mentorPostDTOList = pageContent.stream().map(
                mentorPost -> {
                    List<UserInterest> writerInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
                    return new MentorPostResponse.MentorPostAllWithTimeStampDTO(mentorPost,writerInterests);
                }
        ).collect(Collectors.toList());
        return mentorPostDTOList;
    }

    public void changeMentorPostStatus(MentorPostRequest.StateDTO stateDTO, int id, User writer) {

        isMentor(writer);

        MentoringBoard mentoringBoard = mentorPostJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 글이 존재하지 않습니다."));;

        try {
            mentoringBoard.changeStatus(stateDTO.getMentorPostStateEnum());
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    private void isMentor(User writer) {
        if ( writer.getRole() == Role.MENTEE ) {
            throw new Exception401("해당 사용자는 멘티입니다.");
        }
    }
}


