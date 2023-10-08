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
    public static class MentorPostDTO {
        private int postId;
        private String title;
        private MentorDTO mentor;
        private List<MenteeDTO> mentees;

        public MentorPostDTO(MentorPost mentorPost, MentorDTO mentor, List<MenteeDTO> mentees) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.mentor = mentor;
            this.mentees = mentees;
        }
    }

        @Getter @Setter
        public static class MentorDTO {
            private int mentorId;
            private String profileImage;
            private String name;
            private String country;
            private int age;
            private Role role;
            private List<String> favorites;

            public MentorDTO(User user, List<UserInterest> userInterests) {
                this.mentorId = user.getId();
                this.profileImage = user.getProfileImage();
                this.name = user.getFirstName() + " " + user.getLastName();
                this.country = user.getCountry();
                this.age = user.getAge();
                this.role = user.getRole();
                this.favorites = userInterests.stream()
                        .filter(userInterest -> userInterest.getUser().getId() == user.getId())
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
        @Getter @Setter
        public static class MenteeDTO {
            private int menteeId;
            private String profileImage;
            private String name;
            private String country;
            private int age;
            private Role role;
            private NotConnectedRegisterUser.State state;
            private List<String> favorites; // 고민할 부분 : 유저의 favorite List 를 어떻게 가져올 것 인가?

            /**
             * 유저의 favorite List 를 가져오기 위해
             * userInterest 를 입력으로 받음
             * userInterest 의 userId 와 현재 신청한 멘티 ( notConnectedRegitserUser ) 의 id 값이 일치하는 경우
             * 그럴 경우에만 tag 값들을 가져오기
             * **/

            public MenteeDTO(NotConnectedRegisterUser notConnectedRegisterUser, List<UserInterest> userInterests) {
                this.menteeId = notConnectedRegisterUser.getMenteeUser().getId();
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
