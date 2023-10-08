package com.example.demo.mentoring;

import com.example.demo.account.Account;
import com.example.demo.account.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class MentorPostResponse {

    @Getter
    @Setter
    public static class MentorPostDTO {
        private int postId;
        private String title;
        private String content;
        private WriterDTO writerDTO;

        public MentorPostDTO(MentorPost mentorPost, WriterDTO writerDTO) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.content = mentorPost.getContent();
            this.writerDTO = writerDTO;
        }

        @Getter @Setter
        public static class WriterDTO {
            private int mentorId;
            private String profileImage;
            private String name;
            private String country;
            private Account.Role role;
            private List<String> favorites;

            public WriterDTO(Account account, List<UserInterest> userInterests) {
                this.mentorId = account.getId();
                this.profileImage = account.getProfileImage();
                this.name = account.getFirstname() + " " + account.getLastname();
                this.country = account.getCountry();
                this.role = account.getRole();
                this.favorites = userInterests.stream()
                        .filter(userInterest -> userInterest.getUser().getId() == account.getId())
                        .map(userInterest -> userInterest.getInterest().getTag())
                        .collect(Collectors.toList());
            }
        }
    }
}
