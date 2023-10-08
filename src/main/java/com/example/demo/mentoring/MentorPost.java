package com.example.demo.mentoring;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mentorPost_tb")
public class MentorPost extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @Column(nullable = false)
    private String title;

    private String content;

    @Builder
    public MentorPost(User writer, String title, String content){
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
