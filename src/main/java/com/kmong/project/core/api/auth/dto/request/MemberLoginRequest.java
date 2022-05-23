package com.kmong.project.core.api.auth.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.kmong.project.core.api.auth.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequest {
	@Email(message="이메일 형태를 확인해주세요")
	private final String email;	

    @NotEmpty(message="암호는 필수 값 입니다.")
	private final String password;
    
    public Member toMemeber() {
    	return new Member(email, password);
    }
}
