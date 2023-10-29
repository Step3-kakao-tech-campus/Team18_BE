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
    public List<ContactResponse.ContactDashBoardMenteeDTO> findAllByMentee(int userId) {

        return mentorPostJPARepository.findAllByMenteeUserId(userId).stream()
                .map(this::createMenteeContactDTO)
                .collect(Collectors.toList());
    }

    // contact - mentee 부분 리팩토링 ( DTO 를 만드는 부분 )
    private ContactResponse.ContactDashBoardMenteeDTO createMenteeContactDTO(MentorPost mentorPost) {
        User mentorUser = userJPARepository.findById(mentorPost.getWriter().getId())
                .orElseThrow(() -> new Exception400("해당 사용자가 존재하지 않습니다."));
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentorUser.getId());

        ContactResponse.ContactMentorDTO contactMentorDTO = new ContactResponse.ContactMentorDTO(mentorUser, mentorInterests);

        return new ContactResponse.ContactDashBoardMenteeDTO(mentorPost, contactMentorDTO);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * contact - mentor 화면에서 post 와 mentee 간 엮인 정보들을 조회해서 가져오는 함수
     * **/
    public List<ContactResponse.ContactMentorPostDTO> findAllByMentor(int userId) {

        User mentorUser = userJPARepository.findById(userId)
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentorUser.getId());
        ContactResponse.ContactMentorDTO contactMentorDTO = new ContactResponse.ContactMentorDTO(mentorUser, mentorInterests);

        return mentorPostJPARepository.findAllByWriter(userId).stream()
                .map(mentorPost -> createMentorPostDTO(mentorPost, contactMentorDTO))
                .collect(Collectors.toList());

    }

    // MentorPostDTO 생성 로직
    private ContactResponse.ContactMentorPostDTO createMentorPostDTO(MentorPost mentorPost, ContactResponse.ContactMentorDTO contactMentorDTO) {
        List<ContactResponse.ContactMenteeDTO> contactMenteeDTOS = contactJPARepository.findAllByMentorPostId(mentorPost.getId())
                .stream()
                .map(this::createMenteeDTO)
                .collect(Collectors.toList());

        return new ContactResponse.ContactMentorPostDTO(mentorPost, contactMentorDTO, contactMenteeDTOS);
    }

    // 매핑 로직 분리 ( menteeDTO 생성 로직 )
    private ContactResponse.ContactMenteeDTO createMenteeDTO(NotConnectedRegisterUser mentee) {

        List<UserInterest> menteeInterests = userInterestJPARepository
                .findAllById(mentee.getMenteeUser().getId());

        return new ContactResponse.ContactMenteeDTO(mentee, menteeInterests);
    }

    // contact, done 화면에서 게시글을 조회해서 갯수를 전달해주는 함수
    public ContactResponse.PostCountDTO postCountsByMentor(int userId) {
        // contact 화면에서 게시글을 조회 ( 나중에 where 조건에 state 를 달아야 함 )
        int contactCount = mentorPostJPARepository.countContactByMentorId(userId);
        // done 화면에서 게시글을 조회
        int doneCount = mentorPostJPARepository.countDoneByMentorId(userId);

        return new ContactResponse.PostCountDTO(contactCount, doneCount);
    }
    // contact, done 화면에서 게시글을 조회해서 갯수를 전달해주는 함수 ( 멘티 )
    public ContactResponse.PostCountDTO postCountsMyMentee(int userId) {
        // contact 화면에서 게시글을 조회
        int contactCount = contactJPARepository.countContactByMenteeId(userId);
        // done 화면에서 게시글을 조회
        int doneCount = doneJPARepository.countDoneByMenteeId(userId);

        return new ContactResponse.PostCountDTO(contactCount, doneCount);
    }

    @Transactional
    public void acceptContact(int userId, ContactRequest.ContactAcceptDTO contactAcceptDTO, User user) {
        // 예외 처리
        if ( user.getRole() != Role.MENTOR ) {
            throw new Exception401("해당 사용자는 멘토가 아닙니다.");
        }
        // user 체크
        if (userId != user.getId() || contactAcceptDTO.getMentorId() != user.getId() ) {
            throw new Exception401("올바른 사용자가 아닙니다.");
        }

        int mentorPostId = contactAcceptDTO.getMentorPostId();

        // 현재 멘토가 작성한 글인지 체크
        MentorPost mentorPost = mentorPostJPARepository.findById(mentorPostId)
                .orElseThrow(() -> new Exception404("해당 게시글을 찾을 수 없습니다."));

        // ConnectedUser 에 추가
        for ( ContactRequest.ContactAcceptDTO.AcceptMenteeDTO acceptMenteeDTO : contactAcceptDTO.getMentees() ) {

            // notConnectedRegisterUser 의 state 바꾸기 -> ACCEPT
            NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findByMentorPostIdAndMenteeUserId(mentorPostId, acceptMenteeDTO.getMenteeId())
                    .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

            notConnectedRegisterUser.updateStatus(NotConnectedRegisterUser.State.ACCEPT);

            // ConnectedUser 에 save 하기
            doneJPARepository.save(new ConnectedUser(mentorPost, notConnectedRegisterUser.getMenteeUser()));
        }

    }

    @Transactional
    public void refuseContact(int userId, ContactRequest.ContactRefuseDTO contactRefuseDTO, User user) {
        // 예외 처리
        if ( user.getRole() != Role.MENTOR ) {
            throw new Exception401("해당 사용자는 멘토가 아닙니다.");
        }

        if (userId != user.getId() ) {
            throw new Exception401("올바른 사용자가 아닙니다.");
        }

        int mentorPostId = contactRefuseDTO.getMentorPostId();

        // 멘토와 현재 유저가 같은지 확인
        if ( contactRefuseDTO.getMentorId() != user.getId() ) {
            throw new Exception401("올바른 사용자가 아닙니다.");
        }

        // notConnectedRegisterUser 의 state 바꾸기 -> REFUSE
        for ( ContactRequest.ContactRefuseDTO.RefuseMenteeDTO refuseMenteeDTO : contactRefuseDTO.getMentees() ) {

            NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findByMentorPostIdAndMenteeUserId(mentorPostId, refuseMenteeDTO.getMenteeId())
                    .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

            notConnectedRegisterUser.updateStatus(NotConnectedRegisterUser.State.REFUSE);
       }
    }

    @Transactional
    public void createContact(int userId, ContactRequest.ContactCreateDTO contactCreateDTO, User user) {
        // 예외 처리
        if ( user.getRole() != Role.MENTEE ) {
            throw new Exception401("해당 사용자는 멘티가 아닙니다.");
        }

        if (userId != user.getId() ) {
            throw new Exception401("올바른 사용자가 아닙니다.");
        }

        int mentorPostId = contactCreateDTO.getMentorPostId();

        // 현재 멘토가 작성한 글인지 체크
        MentorPost mentorPost = mentorPostJPARepository.findById(mentorPostId)
                .orElseThrow(() -> new Exception404("해당 게시글을 찾을 수 없습니다."));

        // 이미 신청한 글인지 체크하기
        if ( contactJPARepository.findByMentorPostIdAndMenteeUserId(mentorPostId, userId).isPresent() ) {
            throw new Exception401("이미 신청한 글입니다.");
        }

        // notConnectedRegisterUser 에 save 하기
        contactJPARepository.save(new NotConnectedRegisterUser(mentorPost, user, NotConnectedRegisterUser.State.AWAIT));

    }

    @Transactional
    public void deleteContact(int userId, int mentorPostId, User user) {
        // 예외 처리
        if ( user.getRole() != Role.MENTEE ) {
            throw new Exception401("해당 사용자는 멘티가 아닙니다.");
        }

        // 현재 유저와 userId 값이 일치하는지 확인
        if ( user.getId() != userId ) {
            throw new Exception401("올바른 사용자가 아닙니다.");
        }
        // 해당하는 NotConnectedRegisterUser 가져오기
        NotConnectedRegisterUser notConnectedRegisterUser = contactJPARepository.findByMentorPostIdAndMenteeUserId(mentorPostId, userId)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다." ));

        // notConnectedRegisterUser delete 요청 보내기
        contactJPARepository.deleteById(notConnectedRegisterUser.getId());
    }
}
