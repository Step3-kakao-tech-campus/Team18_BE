package com.example.demo.mentoring;


import com.example.demo.mentoring.contact.ContactStateEnum;
import com.example.demo.mentoring.contact.NotConnectedRegisterUser;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        private MentorPostStateEnum postState;
        private WriterDTO writerDTO;

        public MentorPostAllDTO(MentorPost mentorPost, List<UserInterest> userInterests) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.content = mentorPost.getContent();
            this.postState = mentorPost.getState();
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
            private List<String> interests;

            public WriterDTO(User user, List<UserInterest> userInterests) {
                this.mentorId = user.getId();
                this.profileImage = user.getProfileImage();
                this.name = user.getFirstName() + " " + user.getLastName();
                this.country = user.getCountry();
                this.role = user.getRole();
                this.interests = userInterests.stream()
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
        private MentorPostStateEnum postState;
        private WriterDTO writerDTO;
        private List<ConnectionDTO> connections;

        public MentorPostDTO(MentorPost mentorPost, List<UserInterest> mentorFavorites, List<NotConnectedRegisterUser> mentees, List<UserInterest> menteeInterest) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.content = mentorPost.getContent();
            this.postState = mentorPost.getState();
            MentorPostDTO.WriterDTO writerDTO = new MentorPostDTO.WriterDTO(mentorPost.getWriter(), mentorFavorites);
            this.writerDTO = writerDTO;
            List<ConnectionDTO> connectionDTOList = mentees.stream()
                    .map(mentee -> {
                        List<UserInterest> eachMenteeFavorite = menteeInterest.stream().filter(
                                userInterest -> mentee.getMenteeUser().getId() == userInterest.getUser().getId()
                        ).collect(Collectors.toList());

                        ConnectionDTO connectionDTO = new ConnectionDTO(mentee, eachMenteeFavorite);
                        return connectionDTO;
                    })
                    .collect(Collectors.toList());
            this.connections = connectionDTOList;
        }

        @Getter
        @Setter
        public static class WriterDTO {
            private int mentorId;
            private String profileImage;
            private String name;
            private String country;
            private Role role;
            private List<String> interests;

            public WriterDTO(User user, List<UserInterest> userInterests) {
                this.mentorId = user.getId();
                this.profileImage = user.getProfileImage();
                this.name = user.getFirstName() + " " + user.getLastName();
                this.country = user.getCountry();
                this.role = user.getRole();
                this.interests = userInterests.stream()
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }

        @Getter
        @Setter
        public static class ConnectionDTO {
            private int connectionId;
            private ContactStateEnum state;
            private MenteeDTO mentee;

            public ConnectionDTO(NotConnectedRegisterUser notConnectedRegisterUser, List<UserInterest> userInterests) {
                this.connectionId = notConnectedRegisterUser.getId();
                this.state = notConnectedRegisterUser.getState();
                this.mentee = new MenteeDTO(notConnectedRegisterUser.getMenteeUser(), userInterests);
            }

            @Getter
            @Setter
            public static class MenteeDTO {
                private int menteeId;
                private String profileImage;
                private String name;
                private String country;
                private Role role;
                private LocalDate birthDate;
                private List<String> interests;

                public MenteeDTO(User menteeUser, List<UserInterest> userInterests) {
                    this.menteeId = menteeUser.getId();
                    this.profileImage = menteeUser.getProfileImage();
                    this.name = menteeUser.getFirstName() + " " + menteeUser.getLastName();
                    this.country = menteeUser.getCountry();
                    this.role = menteeUser.getRole();
                    this.birthDate = menteeUser.getBirthDate();
                    this.interests = userInterests.stream()
                            .map(userInterest -> userInterest.getInterest().getCategory())
                            .collect(Collectors.toList());
                }
            }
        }
    }

    @Getter
    @Setter
    public static class MentorPostAllWithTimeStampDTO {
        private int postId;
        private String title;
        private String content;
        private MentorPostStateEnum mentorPostStateEnum;
        private WriterDTO writerDTO;
        private LocalDateTime createdAt;
        private LocalDateTime deletedAt;
        private boolean isDeleted;

        public MentorPostAllWithTimeStampDTO(MentorPost mentorPost, List<UserInterest> userInterests) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.content = mentorPost.getContent();
            this.mentorPostStateEnum = mentorPost.getState();
            WriterDTO writerDTO = new MentorPostAllWithTimeStampDTO.WriterDTO(mentorPost.getWriter(), userInterests);
            this.writerDTO = writerDTO;
            this.createdAt = mentorPost.getCreatedAt();
            this.deletedAt = mentorPost.getDeletedAt();
            this.isDeleted = mentorPost.isDeleted();
        }

        @Getter @Setter
        public static class WriterDTO {
            private int mentorId;
            private String profileImage;
            private String name;
            private String country;
            private Role role;
            private List<String> interests;

            public WriterDTO(User user, List<UserInterest> userInterests) {
                this.mentorId = user.getId();
                this.profileImage = user.getProfileImage();
                this.name = user.getFirstName() + " " + user.getLastName();
                this.country = user.getCountry();
                this.role = user.getRole();
                this.interests = userInterests.stream()
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
    }
}
