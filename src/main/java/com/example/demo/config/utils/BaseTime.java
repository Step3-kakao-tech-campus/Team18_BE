package com.example.demo.config.utils;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.LocalTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {
    @CreatedDate
    @Column(nullable = false)
    private LocalTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
