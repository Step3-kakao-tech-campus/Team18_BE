package com.example.demo.mentoring.contact;

import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;

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
        private ContactMentorDTO mentor;
        private int menteeId;

        public ContactDashBoardMenteeDTO(MentorPost mentorPost, ContactMentorDTO mentor, NotConnectedRegisterUser notConnectedRegisterUser) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.mentor = mentor;
            this.menteeId = notConnectedRegisterUser.getId();
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
        private ContactMentorDTO mentor;
        private List<ContactMenteeDTO> mentees;

        public ContactDashboardMentorDTO(MentorPost mentorPost, ContactMentorDTO mentor, List<ContactMenteeDTO> mentees) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.mentor = mentor;
            this.mentees = mentees;
        }
    }

        @Getter @Setter
        public static class ContactMentorDTO {
            private int mentorId;
            private String profileImage;
            private String name;
            private String country;
            private int age;
            private Role role;
            private List<String> favorites;

            public ContactMentorDTO(User mentor, List<UserInterest> userInterests) {
                this.mentorId = mentor.getId();
                this.profileImage = mentor.getProfileImage();
                this.name = mentor.getFirstName() + " " + mentor.getLastName();
                this.country = mentor.getCountry();
                this.age = mentor.getAge();
                this.role = mentor.getRole();
                this.favorites = userInterests.stream()
                        .filter(userInterest -> userInterest.getUser().getId() == mentor.getId())
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
        @Getter @Setter
        public static class ContactMenteeDTO {
            private int menteeId;
            private String profileImage;
            private String name;
            private String country;
            private int age;
            private Role role;
            private ContactStateEnum state;
            private List<String> favorites; // 고민할 부분 : 유저의 favorite List 를 어떻게 가져올 것 인가?

            /**
             * 유저의 favorite List 를 가져오기 위해
             * userInterest 를 입력으로 받음
             * userInterest 의 userId 와 현재 신청한 멘티 ( notConnectedRegitserUser ) 의 id 값이 일치하는 경우
             * 그럴 경우에만 tag 값들을 가져오기
             * **/

            public ContactMenteeDTO(NotConnectedRegisterUser notConnectedRegisterUser, List<UserInterest> userInterests) {
                this.menteeId = notConnectedRegisterUser.getId();
                this.profileImage = notConnectedRegisterUser.getMenteeUser().getProfileImage();
                this.name = notConnectedRegisterUser.getMenteeUser().getFirstName() + " " + notConnectedRegisterUser.getMenteeUser().getLastName();
                this.country = notConnectedRegisterUser.getMenteeUser().getCountry();
                this.age = notConnectedRegisterUser.getMenteeUser().getAge();
                this.role = notConnectedRegisterUser.getMenteeUser().getRole();
                this.state = notConnectedRegisterUser.getState();
                this.favorites = userInterests.stream()
                        .filter(userInterest -> userInterest.getUser().getId() == notConnectedRegisterUser.getMenteeUser().getId())
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
}
