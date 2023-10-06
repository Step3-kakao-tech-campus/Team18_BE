package com.example.demo.account.userInterest;

import com.example.demo.account.Account;
import com.example.demo.account.interest.Interest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "userInterest")
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Account user;

    @ManyToOne
    private Interest interest;

    @Builder
    public UserInterest(int id, Account account, Interest interest) {
        this.id = id;
        this.user = user;
        this.interest = interest;
    }
}
