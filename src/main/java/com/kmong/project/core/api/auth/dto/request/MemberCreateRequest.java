package com.kmong.project.core.api.auth.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.kmong.project.core.api.auth.domain.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {
	@Schema(description = "이메일", example = "test@kmong.co.kr")
	@Email(message="이메일 형태를 확인해주세요")
	private final String email;	

	@Schema(description = "암호", example = "KmongTest!23")
    @NotEmpty(message="암호는 필수 값 입니다.")
	private String password;
    
    public void passwordEncryption(String password) {
    	this.password = password;
	}
    
    public Member toMemeber() {
    	return new Member(email, password);
    }
}
