package com.example.demo.mentoring.contact;

import com.example.demo.account.Account;
import com.example.demo.mentoring.MentorPost;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notConnectedRegisterUser")
public class NotConnectedRegisterUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private MentorPost mentorPost;

    @ManyToOne
    private Account menteeUser;

    private State state;

    public enum State {
        ACCEPT, REFUSE, AWAIT
    }

    @Builder
    public NotConnectedRegisterUser(int id, MentorPost mentorPost, Account menteeUser, State state) {
        this.id = id;
        this.mentorPost = mentorPost;
        this.menteeUser = menteeUser;
        this.state = state;
    }
}
