package com.example.demo.user;

import com.example.demo.config.utils.BaseTime;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    private String password;

    @Column(nullable = false)
    private String country;

    @Column
    private String introduction;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String phone;

    @Column
    private String profileImage;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public User(String firstName, String lastName, String email, String password, String country, String introduction, int age, String phone, String profileImage, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.country = country;
        this.introduction = introduction;
        this.age = age;
        this.phone = phone;
        this.profileImage = profileImage;
        this.role = role;
    }

    public void updateProfile(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.country = user.getCountry();
        this.introduction = user.getIntroduction();
        this.age = user.getAge();
        this.phone = user.getPhone();
        this.profileImage = user.getProfileImage();
        this.role = user.getRole();
    }
}
