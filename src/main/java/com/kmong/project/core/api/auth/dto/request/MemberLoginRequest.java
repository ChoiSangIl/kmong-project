package com.kmong.project.core.api.auth.dto.request;

import javax.validation.constraints.NotEmpty;

import com.kmong.project.core.api.auth.domain.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequest {
	@Schema(description = "이메일", example = "test@kmong.co.kr")
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    private String email;

	@Schema(description = "암호", example = "KmongTest!23")
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    
    public Member toMemeber() {
    	return new Member(email, password);
    }
}
