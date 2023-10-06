package com.example.demo.mentoring.contact;

import com.example.demo.account.Account;
import com.example.demo.account.AccountJPARepository;
import com.example.demo.account.userInterest.UserInterest;
import com.example.demo.account.userInterest.UserInterestJPARepository;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostJPARepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ContactService {
    // userId 에 해당하는 mentorPost 들을 전부 가져와야 함
    private final MentorPostJPARepostiory mentorPostJPARepostiory;
    private final AccountJPARepository accountJPARepository;
    private final ContactJPARepository contactJPARepository;
    private final UserInterestJPARepository userInterestJPARepository;

    /**
     * findAll : dashboard - contact 부분 화면에 필요한 DTO를 응답해주는 함수
     * parameter : Account ( 유저의 계정 )
     * **/
    public List<ContactResponse.MentorPostDTO> findAll(Account account) {
        // account 의 id 값
        int id = account.getId();

        // 분기 - 유저가 멘토인지, 멘티인지
        if ( account.getRole() == Account.Role.MENTEE ) {
            // TO-DO : Mentee 입장에서 조회하는 부분 작성하기
            return null;
        }
        else {
            /**
             * 흐름 ( 멘토 입장 )
             * 1. 내가 작성한 게시글 ( mentorPosts ) 들을 조회해서 가져온다.
             * 2. 멘토의 정보는 mentorPosts 와 별개로 한번에 조회되는 값이니, for문 밖으로 빼서 조회한다.
             * - 멘토의 정보를 만들기 위해 필요한 값 : mentor 의 account, mentor 의 interests
             * - account 는 조회하면 되니, 바로 구할 수 있다.
             * - userInterest 에서 멘토의 interest 값들을 가져올 수 있기 때문에, userInterest 와 interest 를 join 후 tag 들을 가져온다.
             * 3. mentorPosts 를 활용하여 for문을 돌면서 mentorPost 1개 + mentor 정보 1개 + mentor 정보 여러개 의 꼴로 DTO 를 만든다.
             * - mentor 의 정보를 만들기 위해 필요한 값 : notConnectedRegisterUser 에서 mentor 의 정보, mentor 의 interests
             * - 신청한 멘티들의 정보를 가져오기 위해 notConnectedRegisterUser 의 테이블과 , 각 멘티에 해당하는 interests 들을 묶어서 DTO 로 만들기 ( 그래서 List<MenteeDTO> 를 만듬 )
             * - 싹다 묶어서 reponseDTOs 로 전달
             * **/
            // Return 할 객체
            List<ContactResponse.MentorPostDTO> responseDTOs = new ArrayList<>();

            // 멘토의 id 에 해당하는 mentorPost 다 가져오기
            List<MentorPost> mentorPosts = mentorPostJPARepostiory.findAllByWriter(id);

            // 멘토 정보 가져오기 ( 나중에 User 로 바꿔야 함 )
            Account mentorAccount = accountJPARepository.findById(id);
            // List<UserInterest> 가져오기 ( 멘토꺼 tag 목록 )
            List<UserInterest> mentorInterests = userInterestJPARepository.findAllById(mentorAccount.getId());

            // MentorDTO 담기
            ContactResponse.MentorDTO mentorDTO = new ContactResponse.MentorDTO(mentorAccount, mentorInterests);

            // 만약 3개의 글을 썼을 경우, 현재 mentorPosts 에는 3개의 글 목록이 존재함
            for ( MentorPost mentorPost : mentorPosts ) {
                // List<MenteeDTO> 만들기
                // 신청한 멘티의 목록 ( postId 로 조회 )
                List<NotConnectedRegisterUser> mentees = contactJPARepository.findAllByMentorPostId(mentorPost.getId());

                // List<MenteeDTO> 생성
                List<ContactResponse.MenteeDTO> menteeDTOs = mentees
                        .stream()
                        .map(this::createMenteeDTO)
                        .collect(Collectors.toList());
                // responseDTO 에 담기
                responseDTOs.add(new ContactResponse.MentorPostDTO(mentorPost, mentorDTO, menteeDTOs));

            }
            return responseDTOs;
        }

    }

    // 매핑 로직 분리 ( menteeDTO 생성 로직 )
    private ContactResponse.MenteeDTO createMenteeDTO(NotConnectedRegisterUser mentee) {

        List<UserInterest> menteeInterests = userInterestJPARepository
                .findAllById(mentee.getMenteeUser().getId());

        return new ContactResponse.MenteeDTO(mentee, menteeInterests);
    }

}
