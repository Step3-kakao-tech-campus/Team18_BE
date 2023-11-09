package com.example.demo.mentoring.service;

import com.example.demo.mentoring.domain.ConnectedUser;
import com.example.demo.mentoring.domain.MentoringBoard;
import com.example.demo.mentoring.dto.DoneResponse;
import com.example.demo.mentoring.repository.DoneJPARepository;
import com.example.demo.mentoring.repository.MentorPostJPARepostiory;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserInterest;
import com.example.demo.user.repository.UserInterestJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        MentoringBoard mentoringBoard = connectedUser.getMentoringBoard();
        List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentoringBoard.getWriter().getId());
        DoneResponse.DoneMentorDTO doneMentorDTO = new DoneResponse.DoneMentorDTO(mentoringBoard.getWriter(), mentorInterests);

        List<ConnectedUser> connectedUsers1 = doneJPARepository.findAllByMentorPostId(mentoringBoard.getId());

        List<DoneResponse.DoneMenteeDTO> doneMenteeDTOS = connectedUsers1.stream()
                .map(this::createDoneMenteeDTO)
                .collect(Collectors.toList());

        return new DoneResponse.DoneDashBoardDTO(mentoringBoard, doneMentorDTO, doneMenteeDTOS);
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

    private DoneResponse.DoneDashBoardDTO createMentorPostDTO(MentoringBoard mentoringBoard, DoneResponse.DoneMentorDTO doneMentorDTO) {
        List<DoneResponse.DoneMenteeDTO> doneMenteeDTOS = doneJPARepository.findAllByMentorPostId(mentoringBoard.getId())
                .stream()
                .map(this::createMenteeDTO)
                .collect(Collectors.toList());

        return new DoneResponse.DoneDashBoardDTO(mentoringBoard, doneMentorDTO, doneMenteeDTOS);
    }

    // 매핑 로직 분리 ( menteeDTO 생성 로직 )
    private DoneResponse.DoneMenteeDTO createMenteeDTO(ConnectedUser connectedUser) {

        List<UserInterest> menteeInterests = userInterestJPARepository
                .findAllById(connectedUser.getMenteeUser().getId());

        return new DoneResponse.DoneMenteeDTO(connectedUser, menteeInterests);
    }
}
