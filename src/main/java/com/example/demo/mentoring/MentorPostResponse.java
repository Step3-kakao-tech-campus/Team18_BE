package com.example.demo.mentoring;


import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
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
            private Role role;
            private List<String> favorites;

            public WriterDTO(User user, List<UserInterest> userInterests) {
                this.mentorId = user.getId();
                this.profileImage = user.getProfileImage();
                this.name = user.getFirstName() + " " + user.getLastName();
                this.country = user.getCountry();
                this.role = user.getRole();
                this.favorites = userInterests.stream()
                        .filter(userInterest -> userInterest.getUser().getId() == user.getId())
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
    }
}
