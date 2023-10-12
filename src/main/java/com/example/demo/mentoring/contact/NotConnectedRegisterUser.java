package com.example.demo.mentoring.contact;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.mentoring.MentorPost;
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
@SQLDelete(sql = "UPDATE not_connected_register_user SET deleted_at = CURRENT_TIMESTAMP, isDeleted = TRUE where id = ?")
@Table(name = "notConnectedRegisterUser_tb")
public class NotConnectedRegisterUser extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MentorPost mentorPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User menteeUser;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private State state;

    public enum State {
        ACCEPT, REFUSE, AWAIT
    }

    @Builder
    public NotConnectedRegisterUser(MentorPost mentorPost, User menteeUser, State state) {
        this.mentorPost = mentorPost;
        this.menteeUser = menteeUser;
        this.state = state;
    }
}
