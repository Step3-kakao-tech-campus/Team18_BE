package com.example.demo.mentoring.contact;

import com.example.demo.account.Account;
import com.example.demo.account.userInterest.UserInterest;
import com.example.demo.mentoring.MentorPost;
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
            private Account.Role role;
            private List<String> favorites;

            public MentorDTO(Account account, List<UserInterest> userInterests) {
                this.mentorId = account.getId();
                this.profileImage = account.getProfileImage();
                this.name = account.getFirstname() + " " + account.getLastname();
                this.country = account.getCountry();
                this.age = account.getAge();
                this.role = account.getRole();
                this.favorites = userInterests.stream()
                        .filter(userInterest -> userInterest.getUser().getId() == account.getId())
                        .map(userInterest -> userInterest.getInterest().getTag())
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
            private Account.Role role;
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
                this.name = notConnectedRegisterUser.getMenteeUser().getFirstname() + " " + notConnectedRegisterUser.getMenteeUser().getLastname();
                this.country = notConnectedRegisterUser.getMenteeUser().getCountry();
                this.age = notConnectedRegisterUser.getMenteeUser().getAge();
                this.role = notConnectedRegisterUser.getMenteeUser().getRole();
                this.favorites = userInterests.stream()
                        .filter(userInterest -> userInterest.getUser().getId() == notConnectedRegisterUser.getMenteeUser().getId())
                        .map(userInterest -> userInterest.getInterest().getTag())
                        .collect(Collectors.toList());
            }
        }
}
