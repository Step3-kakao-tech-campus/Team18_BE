package com.example.demo.mentoring;

import com.example.demo.account.Account;
import com.example.demo.mentoring.utils.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mentorPost")
public class MentorPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @Column(nullable = false)
    private String title;

    private String context;

    @Builder
    public MentorPost(int pid, Account writer, String title, String context){
        this.pid = pid;
        this.writer = writer;
        this.title = title;
        this.context = context;
    }
}
