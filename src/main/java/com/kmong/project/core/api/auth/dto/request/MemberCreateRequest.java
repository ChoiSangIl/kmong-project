package com.kmong.project.core.api.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.kmong.project.core.api.auth.domain.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {
	@Schema(description = "이메일", example = "test@kmong.co.kr")
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

	@Schema(description = "암호", example = "KmongTest!23")
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    
    public void passwordEncryption(String password) {
    	this.password = password;
	}
    
    public Member toMemeber() {
    	return new Member(email, password);
    }
}
