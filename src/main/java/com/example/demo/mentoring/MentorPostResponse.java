package com.example.demo.mentoring;


import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

public class MentorPostResponse {

/*
    페이지에서 멘토post 전체를 조회
    MentorPostAllDTO
    클릭시 들어간 페이지에서 보여지는 Post
    MentorPostDTO
 */
    @Getter
    @Setter
    public static class MentorPostAllDTO {
        private int postId;
        private String title;
        private String content;
        private WriterDTO writerDTO;

        public MentorPostAllDTO(MentorPost mentorPost, List<UserInterest> favorites) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.content = mentorPost.getContent();
            WriterDTO writerDTO = new MentorPostAllDTO.WriterDTO(mentorPost.getWriter(), favorites);
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
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
    }

    @Getter
    @Setter
    public static class MentorPostDTO {
        private int postId;
        private String title;
        private String content;
        private WriterDTO writerDTO;
        private List<MenteeDTO> menteeDTOList;

        public MentorPostDTO(MentorPost mentorPost, MentorPostDTO.WriterDTO writerDTO) {
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
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }

        @Getter @Setter
        public static class MenteeDTO{
            private int menteeId;
            private String profileImage;
            private String name;
            private String country;
            private Role role;
            private int age;
            private List<String> favorites;

            public MenteeDTO(User user, List<UserInterest> userInterests) {
                this.menteeId = user.getId();
                this.profileImage = user.getProfileImage();
                this.name = user.getFirstName() + " " + user.getLastName();
                this.country = user.getCountry();
                this.role = user.getRole();
                this.age = user.getAge();
                this.favorites = userInterests.stream()
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
    }
}
