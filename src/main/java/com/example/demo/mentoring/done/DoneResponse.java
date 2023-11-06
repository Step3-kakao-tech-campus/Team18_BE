package com.example.demo.mentoring.done;

import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class DoneResponse {
    @Getter
    @Setter
    public static class DoneDashBoardDTO {
        private int postId;
        private String title;
        private DoneMentorDTO mentor;
        private List<DoneMenteeDTO> mentees;

        public DoneDashBoardDTO(MentorPost mentorPost, DoneMentorDTO mentor, List<DoneMenteeDTO> mentees) {
            this.postId = mentorPost.getId();
            this.title = mentorPost.getTitle();
            this.mentor = mentor;
            this.mentees = mentees;
        }
    }

    @Getter @Setter
    public static class DoneMentorDTO {
        private int mentorId;
        private String profileImage;
        private String name;
        private String country;
        private int age;
        private Role role;
        private List<String> favorites;

        public DoneMentorDTO(User mentor, List<UserInterest> userInterests) {
            this.mentorId = mentor.getId();
            this.profileImage = mentor.getProfileImage();
            this.name = mentor.getFirstName() + " " + mentor.getLastName();
            this.country = mentor.getCountry();
            this.age = mentor.getAge();
            this.role = mentor.getRole();
            this.favorites = userInterests.stream()
                    .map(userInterest -> userInterest.getInterest().getCategory())
                    .collect(Collectors.toList());
        }
    }
    @Getter @Setter
    public static class DoneMenteeDTO {
        private int menteeId;
        private String profileImage;
        private String name;
        private String country;
        private int age;
        private Role role;
        private List<String> favorites;
        public DoneMenteeDTO(ConnectedUser connectedUser, List<UserInterest> userInterests) {
            this.menteeId = connectedUser.getId();
            this.profileImage = connectedUser.getMenteeUser().getProfileImage();
            this.name = connectedUser.getMenteeUser().getFirstName() + " " + connectedUser.getMenteeUser().getLastName();
            this.country = connectedUser.getMenteeUser().getCountry();
            this.age = connectedUser.getMenteeUser().getAge();
            this.role = connectedUser.getMenteeUser().getRole();
            this.favorites = userInterests.stream()
                    .map(userInterest -> userInterest.getInterest().getCategory())
                    .collect(Collectors.toList());
        }
    }
}
