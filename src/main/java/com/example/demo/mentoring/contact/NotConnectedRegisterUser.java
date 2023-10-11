package com.example.demo.mentoring.contact;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notConnectedRegisterUser_tb")
public class NotConnectedRegisterUser extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    private MentorPost mentorPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User menteeUser;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private State state;

    public enum State {
        ACCEPT, REFUSE, AWAIT
    }

    @Builder
    public NotConnectedRegisterUser(int id, MentorPost mentorPost, User menteeUser, State state) {
        this.id = id;
        this.mentorPost = mentorPost;
        this.menteeUser = menteeUser;
        this.state = state;
    }
}
