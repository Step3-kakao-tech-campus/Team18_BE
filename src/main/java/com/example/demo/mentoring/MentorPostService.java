package com.example.demo.mentoring;

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
        MentorPost mentorPost = mentorPostJPARepository.findById(id);

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
        Optional<MentorPost> optionalMentorPost = Optional.ofNullable(mentorPostJPARepository.findById(id));

        if(optionalMentorPost.isPresent())
        {
            MentorPost mentorPost = optionalMentorPost.get();
            mentorPost.update(createDTO.getTitle(), createDTO.getContent());
        }
        else
        {
            // 예외처리

        }
    }
}


