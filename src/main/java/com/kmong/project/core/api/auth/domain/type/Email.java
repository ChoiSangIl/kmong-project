package com.kmong.project.core.api.auth.domain.type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
	@Column(name="EMAIL", unique = true)
	private String value;
	
	public Email(String email){
		this.value = email;
	}
}
