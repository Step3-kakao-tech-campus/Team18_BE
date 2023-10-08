package com.example.demo.mentoring.done;

import com.example.demo.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "connectedUser")
public class ConnectedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User mentorUser;

    @ManyToOne
    private User menteeUser;

    @Builder
    public ConnectedUser(int id, User mentorUser, User menteeUser) {
        this.id = id;
        this.mentorUser = mentorUser;
        this.menteeUser = menteeUser;
    }

}
