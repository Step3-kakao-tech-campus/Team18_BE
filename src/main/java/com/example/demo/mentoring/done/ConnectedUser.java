package com.example.demo.mentoring.done;

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
@SQLDelete(sql = "UPDATE connected_user_tb SET deleted_at = CURRENT_TIMESTAMP, isDeleted = TRUE where id = ?")
@Table(name = "connectedUser_tb")
public class ConnectedUser extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User mentorUser;

    @ManyToOne
    private User menteeUser;

    @Builder
    public ConnectedUser(int id, User mentorUser, User menteeUser) {
        this.id = id;
        this.mentorUser = mentorUser;
        this.menteeUser = menteeUser;
    }

}
