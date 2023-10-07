package com.example.demo.mentoring;

import com.example.demo.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mentorPost")
public class MentorPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @Column(nullable = false)
    private String title;

    private String context;

    @Builder
    public MentorPost(int id, Account writer, String title, String context){
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.context = context;
    }
}