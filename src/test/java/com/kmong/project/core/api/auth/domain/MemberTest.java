package com.kmong.project.core.api.auth.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 객체 테스트")
public class MemberTest {
	private Member member;
	private static final String email = "test@kmong.co.kr";
	private static final String password = "kmongTest!23";
	
	@Test
	@DisplayName("회원을 생성할 수 있어야한다.")
	public void create() {
		//when
		member = new Member(email, password);
		
		//then
		assertAll(
				()->assertEquals(member.getEmail().getValue(), email),
				()->assertEquals(member.getPassword().getValue(), password)
		);
	}
}
