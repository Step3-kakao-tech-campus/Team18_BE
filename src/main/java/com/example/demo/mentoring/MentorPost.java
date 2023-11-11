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
@SQLDelete(sql = "UPDATE mentor_posts SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "mentor_posts")
public class MentorPost extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300)
    private String content;

    @Convert(converter = MentorPostStateConverter.class)
    @Column(name = "state", nullable = false)
    private MentorPostStateEnum state = MentorPostStateEnum.ACTIVE;

    @Builder
    public MentorPost(User writer, String title, String content){
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content)
    {
        this.title = title;
        this.content = content;
    }

    public void changeStatus(MentorPostStateEnum mentorPostStateEnum)
    {
        this.state = mentorPostStateEnum;
    }
}
