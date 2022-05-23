package com.kmong.project.core.api.auth.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kmong.project.common.domain.BaseEntity;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Email email;

	@Embedded
	private Password password;
	
	public Member(String email, String password) {
		this.email = new Email(email);
		this.password = new Password(password);
	}
	
	public Member(Long id, String email, String password) {
		this.id = id;
		this.email = new Email(email);
		this.password = new Password(password);
	}
}
