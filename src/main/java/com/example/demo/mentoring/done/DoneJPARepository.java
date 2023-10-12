package com.example.demo.mentoring.done;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoneJPARepository extends JpaRepository<ConnectedUser, Integer> {

}
