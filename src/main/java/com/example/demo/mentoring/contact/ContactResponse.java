package com.example.demo.mentoring.contact;

import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ContactResponse {

    @Getter @Setter
    public static class PostCountDTO {
        private int contactCount;
        private int doneCount;

        public PostCountDTO(int contactCount, int doneCount) {
            this.contactCount = contactCount;
            this.doneCount = doneCount;
        }
    }

    @Getter @Setter
    public static class ContactDashBoardMenteeDTO {
        private int postId;
        private String title;
        private ContactMentorDTO writerDTO;
        private int connectionId;

        public ContactDashBoardMenteeDTO(MentorPost mentorPost, ContactMentorDTO writerDTO, NotConnectedRegisterUser notConnectedRegisterUser) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.writerDTO = writerDTO;
            this.connectionId = notConnectedRegisterUser.getId();
        }
    }
    /**
     *
     * DTO 담는 순서
     * 1. MenteeDTO : Parameter ( NotConnectedRegisterUser, List<UserInterest> )
     * 여기서 NotConnectedRegisterUser 를 리팩토링해서, 아예 Mentee 부분만 빼와도 될듯 하다.
     * 2. MentorDTO : Parameter ( Account -> 나중에 User 로 바꿔야 함, List<UserInterest> -> mentor 의 tags )
     * 3. MentorPostDTO : Parameter ( MentorPost, List<MentorDTO>, List<MenteeDTO> )
     *
     * **/
    @Getter @Setter
    public static class ContactDashboardMentorDTO {
        private int postId;
        private String title;
        private ContactMentorDTO writerDTO;
        private List<ConnectionDTO> mentees;

        public ContactDashboardMentorDTO(MentorPost mentorPost, ContactMentorDTO writerDTO, List<ConnectionDTO> mentees) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.writerDTO = writerDTO;
            this.mentees = mentees;
        }
    }

    @Getter @Setter
    public static class ContactMentorDTO {
        private int mentorId;
        private String profileImage;
        private String name;
        private String country;
        private LocalDate birthDate;
        private Role role;
        private List<String> favorites;

        public ContactMentorDTO(User mentor, List<UserInterest> userInterests) {
            this.mentorId = mentor.getId();
            this.profileImage = mentor.getProfileImage();
            this.name = mentor.getFirstName() + " " + mentor.getLastName();
            this.country = mentor.getCountry();
            this.birthDate = mentor.getBirthDate();
            this.role = mentor.getRole();
            this.favorites = userInterests.stream()
                    .map(userInterest -> userInterest.getInterest().getCategory())
                    .collect(Collectors.toList());
        }
    }
    @Getter @Setter
    public static class ConnectionDTO {
        private int connectionId;
        private ContactStateEnum state;
        private MenteeDTO mentee;

        // ConnectionDTO 생성자
        public ConnectionDTO(NotConnectedRegisterUser notConnectedRegisterUser, List<UserInterest> userInterests) {
            this.connectionId = notConnectedRegisterUser.getId();
            this.state = notConnectedRegisterUser.getState();
            this.mentee = new MenteeDTO(notConnectedRegisterUser.getMenteeUser(), userInterests);
        }

        // MenteeDTO 내부 클래스
        @Getter @Setter
        public static class MenteeDTO {
            private int menteeId;
            private String profileImage;
            private String name;
            private String country;
            private LocalDate birthDate;
            private Role role;
            private List<String> favorites;

            // MenteeDTO 생성자
            public MenteeDTO(User menteeUser, List<UserInterest> userInterests) {
                this.menteeId = menteeUser.getId();
                this.profileImage = menteeUser.getProfileImage();
                this.name = menteeUser.getFirstName() + " " + menteeUser.getLastName();
                this.country = menteeUser.getCountry();
                this.birthDate = menteeUser.getBirthDate();
                this.role = menteeUser.getRole();
                this.favorites = userInterests.stream()
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
    }
}
