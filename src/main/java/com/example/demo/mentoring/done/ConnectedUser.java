package com.example.demo.mentoring.done;

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
@SQLDelete(sql = "UPDATE connected_users SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "connected_users",
        indexes = {
                @Index(name = "not_connected_register_users_mentor_post_id_idx", columnList = "mentor_post_id"),
                @Index(name = "not_connected_register_users_mentee_user_id_idx", columnList = "mentee_user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_connected_user_mentor_post_mentee_user", columnNames = {"mentor_post_id", "mentee_user_id"})
        })
public class ConnectedUser extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MentorPost mentorPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User menteeUser;

    @Builder
    public ConnectedUser(MentorPost mentorPost, User menteeUser) {
        this.mentorPost = mentorPost;
        this.menteeUser = menteeUser;
    }

}
