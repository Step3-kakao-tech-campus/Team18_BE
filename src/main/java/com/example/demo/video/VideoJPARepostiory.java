package com.example.demo.video;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoJPARepostiory extends JpaRepository<User, Integer> {
}
