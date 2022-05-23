package com.kmong.project.core.api.auth.domain.type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;

@Getter
@Embeddable
public class Password {

    @Column(name="password")
	private String value;
	
	protected Password(){
	}
	
	public Password(String password){
		this.value = password;
	}
	
}
