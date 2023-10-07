package com.example.demo.user;

import com.example.demo.config.utils.BaseTime;
import com.example.demo.Interest.Interest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_interest")
public class UserInterest extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @Builder
    public UserInterest(int id, User user, Interest interest) {
        this.id = id;
        this.user = user;
        this.interest = interest;
    }
}
