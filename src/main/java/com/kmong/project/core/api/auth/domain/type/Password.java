package com.kmong.project.core.api.auth.domain.type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(name="PASSWORD")
	private String value;
	
	public Password(String password){
		this.value = password;
	}
	
}
