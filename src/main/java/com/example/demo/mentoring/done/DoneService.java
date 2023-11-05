package com.example.demo.mentoring.done;

import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import com.example.demo.mentoring.contact.ContactResponse;
import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import com.example.demo.user.userInterest.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DoneService {

    private final DoneJPARepository doneJPARepository;
    private final MentorPostJPARepostiory mentorPostJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;

    /**
     * 멘티 : done 화면 조회 기능
     * **/
    public List<DoneResponse.DoneDashBoardDTO> findByMentee(User mentee) {
        return doneJPARepository.findAllByMenteeId(mentee.getId()).stream()
                .map(this::createDoneDashBoardDTO)
                .collect(Collectors.toList());
    }

    private DoneResponse.DoneDashBoardDTO createDoneDashBoardDTO(ConnectedUser connectedUser) {
        MentorPost mentorPost = connectedUser.getMentorPost();
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentorPost.getWriter().getId());
        DoneResponse.DoneMentorDTO doneMentorDTO = new DoneResponse.DoneMentorDTO(mentorPost.getWriter(), mentorInterests);

        List<ConnectedUser> connectedUsers1 = doneJPARepository.findAllByMentorPostId(mentorPost.getId());

        List<DoneResponse.DoneMenteeDTO> doneMenteeDTOS = connectedUsers1.stream()
                .map(this::createDoneMenteeDTO)
                .collect(Collectors.toList());

        return new DoneResponse.DoneDashBoardDTO(mentorPost, doneMentorDTO, doneMenteeDTOS);
    }

    private DoneResponse.DoneMenteeDTO createDoneMenteeDTO(ConnectedUser connectedUser) {
        List<UserInterest> menteeInterests = userInterestJPARepository.findAllById(connectedUser.getMenteeUser().getId());
        return new DoneResponse.DoneMenteeDTO(connectedUser, menteeInterests);
    }

    public List<DoneResponse.DoneDashBoardDTO> findByMentor(User mentor) {
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentor.getId());
        DoneResponse.DoneMentorDTO doneMentorDTO = new DoneResponse.DoneMentorDTO(mentor, mentorInterests);

        return mentorPostJPARepository.findAllByWriterDone(mentor.getId()).stream()
                .map(mentorPost -> createMentorPostDTO(mentorPost, doneMentorDTO))
                .collect(Collectors.toList());
    }

    private DoneResponse.DoneDashBoardDTO createMentorPostDTO(MentorPost mentorPost, DoneResponse.DoneMentorDTO doneMentorDTO) {
        List<DoneResponse.DoneMenteeDTO> doneMenteeDTOS = doneJPARepository.findAllByMentorPostId(mentorPost.getId())
                .stream()
                .map(this::createMenteeDTO)
                .collect(Collectors.toList());

        return new DoneResponse.DoneDashBoardDTO(mentorPost, doneMentorDTO, doneMenteeDTOS);
    }

    // 매핑 로직 분리 ( menteeDTO 생성 로직 )
    private DoneResponse.DoneMenteeDTO createMenteeDTO(ConnectedUser connectedUser) {

        List<UserInterest> menteeInterests = userInterestJPARepository
                .findAllById(connectedUser.getMenteeUser().getId());

        return new DoneResponse.DoneMenteeDTO(connectedUser, menteeInterests);
    }
}
