package com.example.demo.mentoring.done;

import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.userInterest.UserInterest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
        private LocalDate birthDate;
        private Role role;
        private List<String> favorites;

        public DoneMentorDTO(User mentor, List<UserInterest> userInterests) {
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
    @Getter
    @Setter
    public static class DoneMenteeDTO {
        private int doneId; // 이 필드는 사용되지 않는 것으로 보입니다. 필요하다면 추가하십시오.
        private MenteeDTO mentee;

        public DoneMenteeDTO(ConnectedUser connectedUser, List<UserInterest> userInterests) {
            this.doneId = connectedUser.getId();
            this.mentee = new MenteeDTO(connectedUser, userInterests);
        }

        // MenteeDTO 클래스 내부 필드를 직접 사용하지 않고, MenteeDTO 객체를 통해 접근
        @Getter
        @Setter
        public static class MenteeDTO {
            private int menteeId;
            private String profileImage;
            private String name;
            private String country;
            private LocalDate birthDate;
            private Role role;
            private List<String> favorites;

            public MenteeDTO(ConnectedUser connectedUser, List<UserInterest> userInterests) {
                this.menteeId = connectedUser.getMenteeUser().getId();
                this.profileImage = connectedUser.getMenteeUser().getProfileImage();
                this.name = connectedUser.getMenteeUser().getFirstName() + " " + connectedUser.getMenteeUser().getLastName();
                this.country = connectedUser.getMenteeUser().getCountry();
                this.birthDate = connectedUser.getMenteeUser().getBirthDate();
                this.role = connectedUser.getMenteeUser().getRole();
                this.favorites = userInterests.stream()
                        .map(userInterest -> userInterest.getInterest().getCategory())
                        .collect(Collectors.toList());
            }
        }
    }
}
