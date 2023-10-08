package com.example.demo.mentoring;

import com.example.demo.account.Account;
import com.example.demo.config.utils.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mentorPost")
public class MentorPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @Column(nullable = false)
    private String title;

    private String content;

    @Builder
    public MentorPost(Account writer, String title, String content){
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
