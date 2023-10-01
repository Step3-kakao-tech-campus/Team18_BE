package com.example.demo.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String country;

    @Column
    private String introduction;

    @Column(nullable = false)
    private int age;

    @Column
    private String profileImage;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN, MENTOR, MENTEE
    }

    @Builder
    public Account(int uid, String email, String password, String firstname, String lastname, String country, String introduction, int age, String profileImage, Role role){
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.introduction = introduction;
        this.age = age;
        this.profileImage = profileImage;
        this.role = role;
    }
}
