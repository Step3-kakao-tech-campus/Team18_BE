package com.example.demo.mentoring;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE mentorPost_tb SET deleted_at = CURRENT_TIMESTAMP, isDeleted = TRUE where id = ?")
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
