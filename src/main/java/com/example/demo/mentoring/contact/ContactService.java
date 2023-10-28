package com.example.demo.mentoring.contact;

import com.example.demo.config.errors.exception.Exception400;
import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception404;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import com.example.demo.mentoring.done.ConnectedUser;
import com.example.demo.mentoring.done.DoneJPARepository;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ContactService {

    private final MentorPostJPARepostiory mentorPostJPARepository;
    private final UserJPARepository userJPARepository;
    private final ContactJPARepository contactJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;
    private final DoneJPARepository doneJPARepository;

    /**
     * contact - mentee 화면에서 mentor 가 작성한 글에 신청을 누른 게시글들을 가져오는 함수
     * **/
    public List<ContactResponse.MenteeContactDTO> findAllByMentee(int userId) {

        return mentorPostJPARepository.findAllByMenteeUserId(userId).stream()
                .map(this::createMenteeContactDTO)
                .collect(Collectors.toList());
    }

    // contact - mentee 부분 리팩토링 ( DTO 를 만드는 부분 )
    private ContactResponse.MenteeContactDTO createMenteeContactDTO(MentorPost mentorPost) {
        User mentorUser = userJPARepository.findById(mentorPost.getWriter().getId())
                .orElseThrow(() -> new Exception400("해당 사용자가 존재하지 않습니다."));
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentorUser.getId());

        ContactResponse.MentorDTO mentorDTO = new ContactResponse.MentorDTO(mentorUser, mentorInterests);

        return new ContactResponse.MenteeContactDTO(mentorPost, mentorDTO);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * contact - mentor 화면에서 post 와 mentee 간 엮인 정보들을 조회해서 가져오는 함수
     * **/
    public List<ContactResponse.MentorPostDTO> findAllByMentor(int userId) {

        User mentorUser = userJPARepository.findById(userId)
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentorUser.getId());
        ContactResponse.MentorDTO mentorDTO = new ContactResponse.MentorDTO(mentorUser, mentorInterests);

        return mentorPostJPARepository.findAllByWriter(userId).stream()
                .map(mentorPost -> createMentorPostDTO(mentorPost, mentorDTO))
                .collect(Collectors.toList());

    }

    // MentorPostDTO 생성 로직
    private ContactResponse.MentorPostDTO createMentorPostDTO(MentorPost mentorPost, ContactResponse.MentorDTO mentorDTO) {
        List<ContactResponse.MenteeDTO> menteeDTOs = contactJPARepository.findAllByMentorPostId(mentorPost.getId())
                .stream()
                .map(this::createMenteeDTO)
                .collect(Collectors.toList());

        return new ContactResponse.MentorPostDTO(mentorPost, mentorDTO, menteeDTOs);
    }

    // 매핑 로직 분리 ( menteeDTO 생성 로직 )
    private ContactResponse.MenteeDTO createMenteeDTO(NotConnectedRegisterUser mentee) {

        List<UserInterest> menteeInterests = userInterestJPARepository
                .findAllById(mentee.getMenteeUser().getId());

        return new ContactResponse.MenteeDTO(mentee, menteeInterests);
    }

    // contact, done 화면에서 게시글을 조회해서 갯수를 전달해주는 함수
    public ContactResponse.postCountDTO postCountsByMentor(int userId) {
        // contact 화면에서 게시글을 조회 ( 나중에 where 조건에 state 를 달아야 함 )
        int contactCount = mentorPostJPARepository.countContactByMentorId(userId);
        // done 화면에서 게시글을 조회
        int doneCount = mentorPostJPARepository.countDoneByMentorId(userId);

        return new ContactResponse.postCountDTO(contactCount, doneCount);
    }
    // contact, done 화면에서 게시글을 조회해서 갯수를 전달해주는 함수 ( 멘티 )
    public ContactResponse.postCountDTO postCountsMyMentee(int userId) {
        // contact 화면에서 게시글을 조회
        int contactCount = contactJPARepository.countContactByMenteeId(userId);
        // done 화면에서 게시글을 조회
        int doneCount = doneJPARepository.countDoneByMenteeId(userId);

        return new ContactResponse.postCountDTO(contactCount, doneCount);
    }

    @Transactional
    public void acceptContact(int id, ContactRequest.AcceptDTO acceptDTO, User user) {
        // 예외 처리
        if ( user.getRole() != Role.MENTOR ) {
            throw new Exception401("해당 사용자는 멘토가 아닙니다.");
        }

        if (id != user.getId() ) {
            throw new Exception401("올바른 사용자가 아닙니다.");
        }

        int mentorPostId = acceptDTO.getMentorPostId();

        // 현재 멘토가 작성한 글인지 체크
        MentorPost mentorPost = mentorPostJPARepository.findById(mentorPostId)
                .orElseThrow(() -> new Exception404("해당 게시글을 찾을 수 없습니다."));

        // ConnectedUser 에 추가
        for ( ContactRequest.AcceptDTO.MentorAndMenteeDTO mentorAndMenteeDTO : acceptDTO.getMentorsAndMentees() ) {

            // 멘토가 현재 유저와 같은지 확인
            if ( mentorAndMenteeDTO.getMentorId() != user.getId() ) {
                throw new Exception401("올바른 사용자가 아닙니다.");
            }

            // notConnectedRegisterUser 의 state 바꾸기 -> ACCEPT
            NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findByMentorPostIdAndMenteeUserId(mentorPostId, mentorAndMenteeDTO.getMenteeId())
                    .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

            notConnectedRegisterUser.updateStatus(NotConnectedRegisterUser.State.ACCEPT);

            // ConnectedUser 에 save 하기
            doneJPARepository.save(new ConnectedUser(mentorPost, notConnectedRegisterUser.getMenteeUser()));
        }

    }

}
