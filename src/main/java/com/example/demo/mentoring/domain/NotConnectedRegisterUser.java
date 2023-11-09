package com.example.demo.mentoring.domain;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.user.domain.User;
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
@Table(name = "not_connected_register_users")
public class NotConnectedRegisterUser extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MentoringBoard mentoringBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private User menteeUser;

    @Convert(converter = ContactStateConverter.class)
    @Column(name = "state", nullable = false)
    private ContactStateEnum state;

    public void updateStatus(ContactStateEnum state) {
        this.state = state;
    }

    @Builder
    public NotConnectedRegisterUser(MentoringBoard mentoringBoard, User menteeUser, ContactStateEnum state) {
        this.mentoringBoard = mentoringBoard;
        this.menteeUser = menteeUser;
        this.state = state;
    }
}
