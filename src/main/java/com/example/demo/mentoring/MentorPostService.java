package com.example.demo.mentoring;

import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception403;
import com.example.demo.config.errors.exception.Exception500;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.mentoring.contact.ContactJPARepository;
import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
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
    public MentorPostResponse.MentorPostIdDTO createMentorPost(MentorPostRequest.CreateMentorPostDTO createMentorPostDTO, User writer) {
        isMentor(writer);

        Pageable recentPostTime = PageRequest.of(0, 1);
        Page<LocalDateTime> recentPostDate = mentorPostJPARepository.createdRecentPost(writer.getId(), recentPostTime);
        if(recentPostDate != null && !recentPostDate.getContent().isEmpty())
        {
            Duration duration = Duration.between(recentPostDate.getContent().get(0), LocalDateTime.now());
            if (duration.toMinutes() < 5) {
                throw new Exception403("5분이내로 글을 작성 할 수 없습니다.");
            }
        }

        MentorPost mentorPost = new MentorPost( writer, createMentorPostDTO.getTitle(), createMentorPostDTO.getContent());

        mentorPostJPARepository.save(mentorPost);

        return new MentorPostResponse.MentorPostIdDTO(mentorPost);
    }

   /* 1. mentorPostList를 조회
    2. 각 List당 writer별 writerInterests를 조회
    3. MentorPostDTO 생성*/
    public List<MentorPostResponse.MentorPostAllDTO> findAllMentorPost(String search, String keyword, int page) {
        Pageable pageable = PageRequest.of(page,5);

        MentorSearchCategory mentorSearchCategory = MentorSearchCategory.valueOf(search.toUpperCase(Locale.ROOT));

        Page<MentorPost> result = mentorSearchCategory.execute(keyword, pageable, mentorPostJPARepository);
        List<MentorPostResponse.MentorPostAllDTO> mentorPostAllDTOS = result.stream().map(mentorPost -> {
            List<UserInterest> writerInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
            return new MentorPostResponse.MentorPostAllDTO(mentorPost, writerInterests);
        }).collect(Collectors.toList());

        return mentorPostAllDTOS;
    }

    public MentorPostResponse.MentorPostDTO findMentorPost(int id){
        MentorPost mentorPost = mentorPostJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 글이 존재하지 않습니다.\n" + "id : " + id));

        //writer 데이터
        //writer를 제외하고는 다 null 가능
        User mentor = mentorPost.getWriter();
        //mentee들 데이터
        List<NotConnectedRegisterUser> menteeList = contactJPARepository.findAllByMentorPostId(id);
        //writer Interest데이터
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentor.getId());
        //mentee들 Interest데이터
        List<UserInterest> menteeInterests = menteeList.stream()
                .flatMap(mentee -> userInterestJPARepository.findAllById(mentee.getMenteeUser().getId()).stream())
                .collect(Collectors.toList());

        return new MentorPostResponse.MentorPostDTO(mentorPost, mentorInterests, menteeList, menteeInterests);
    }
    @Transactional
    public void updateMentorPost(MentorPostRequest.CreateMentorPostDTO createMentorPostDTO, int id, User writer) {
        isMentor(writer);

        MentorPost mentorPost = mentorPostJPARepository.findById(id).
                orElseThrow(() -> new Exception404("해당 글이 존재하지 않습니다."));

        mentorPost.update(createMentorPostDTO.getTitle(), createMentorPostDTO.getContent());

    }

    public void deleteMentorPost(int id, User writer) {
        isMentor(writer);

        mentorPostJPARepository.deleteById(id);
    }

    public void changeMentorPostStatus(MentorPostRequest.StateDTO stateDTO, int id, User writer) {

        isMentor(writer);

        MentorPost mentorPost = mentorPostJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 글이 존재하지 않습니다."));

        mentorPost.changeStatus(stateDTO.getMentorPostStateEnum());
    }

    private void isMentor(User writer) {
        if ( writer.getRole() == Role.MENTEE ) {
            throw new Exception401("권한이 없습니다");
        }
    }
}


