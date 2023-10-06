package com.example.demo.mentoring.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactJPARepository extends JpaRepository<NotConnectedRegisterUser, Integer> {

    List<NotConnectedRegisterUser> findAllByMentorPostId(int mentorPostId);

}
