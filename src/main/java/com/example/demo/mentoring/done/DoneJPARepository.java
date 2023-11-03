package com.example.demo.mentoring.done;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoneJPARepository extends JpaRepository<ConnectedUser, Integer> {

}
