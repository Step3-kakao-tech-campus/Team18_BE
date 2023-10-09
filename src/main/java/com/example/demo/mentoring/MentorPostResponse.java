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

        public MentorPostAllDTO(MentorPost mentorPost, List<UserInterest> userInterests) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.content = mentorPost.getContent();
            WriterDTO writerDTO = new MentorPostAllDTO.WriterDTO(mentorPost.getWriter(), userInterests);
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

    /*
    param :
    MentorPost mentorPost 멘토 데이터
    List<UserInterest> mentorInterests 멘토의 Interest
    List<NotConnectedRegisterUser> mentees 멘티들 데이터
    List<UserInterest> menteeInterests : 멘티들 각각의 Interest 리스트 전체

    dto :
    writerDTO : 작성자인 멘토의 DTO
    menteeDTO : 멘티들의 DTO
     */
    @Getter
    @Setter
    public static class MentorPostDTO {
        private int postId;
        private String title;
        private String content;
        private WriterDTO writerDTO;
        private List<MenteeDTO> menteeDTOList;

        public MentorPostDTO(MentorPost mentorPost, List<UserInterest> mentorFavorites, List<NotConnectedRegisterUser> mentees, List<UserInterest> menteeInterest) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.content = mentorPost.getContent();
            MentorPostDTO.WriterDTO writerDTO = new MentorPostDTO.WriterDTO(mentorPost.getWriter(), mentorFavorites);
            this.writerDTO = writerDTO;
            List<MentorPostDTO.MenteeDTO> menteeDTOList = mentees.stream()
                    .map(mentee -> {
                        List<UserInterest> eachMenteeFavorite = menteeInterest.stream().filter(
                                userInterest -> mentee.getId() == userInterest.getId()
                        ).collect(Collectors.toList());
                        MentorPostDTO.MenteeDTO menteeDTO = new MentorPostDTO.MenteeDTO(mentee.getMenteeUser(), eachMenteeFavorite);
                        return menteeDTO;
                    })
                    .collect(Collectors.toList());
            this.menteeDTOList = menteeDTOList;
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
