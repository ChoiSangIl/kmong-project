package com.kmong.project.common.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class BaseEntity {
	@CreationTimestamp
	@Column(name= "create_at", updatable = false)
    protected LocalDateTime createdTime;
	
	@UpdateTimestamp
	@Column(name= "update_at")
	@LastModifiedDate
    protected LocalDateTime modifiedTime;
}
