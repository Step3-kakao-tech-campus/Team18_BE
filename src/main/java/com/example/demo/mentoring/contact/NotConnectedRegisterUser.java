package com.example.demo.mentoring.contact;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.mentoring.MentorPost;
import com.example.demo.mentoring.MentorPostStateConverter;
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
@SQLDelete(sql = "UPDATE not_connected_register_users SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Table(name = "not_connected_register_users",
    indexes = {
        @Index(name = "not_connected_register_users_mentor_post_id_idx", columnList = "mentor_post_id"),
        @Index(name = "not_connected_register_users_mentee_user_id_idx", columnList = "mentee_user_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_not_connected_register_user_mentor_post_mentee_user", columnNames = {"mentor_post_id", "mentee_user_id"})
    })
public class NotConnectedRegisterUser extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MentorPost mentorPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User menteeUser;

    @Convert(converter = ContactStateConverter.class)
    @Column(name = "state", nullable = false)
    private ContactStateEnum state;

    public void updateStatus(ContactStateEnum state) {
        this.state = state;
    }

    @Builder
    public NotConnectedRegisterUser(MentorPost mentorPost, User menteeUser, ContactStateEnum state) {
        this.mentorPost = mentorPost;
        this.menteeUser = menteeUser;
        this.state = state;
    }
}
