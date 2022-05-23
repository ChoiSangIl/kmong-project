package com.kmong.project.core.api.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.MemberRepository;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;
import com.kmong.project.core.api.auth.dto.request.MemberCreateRequest;
import com.kmong.project.core.api.auth.dto.request.MemberLoginRequest;
import com.kmong.project.core.api.auth.dto.response.MemberCreateResponse;
import com.kmong.project.core.api.auth.dto.response.MemberLoginResponse;
import com.kmong.project.core.api.auth.service.MemberService;
import com.kmong.project.security.JwtTokenProvider;

public class MemberServiceImplTest {
	private static final Email email = new Email("test@kmong.co.kr");
	private static final Password password = new Password("kmongTest!23");
	private static final String jwtToken = "jwtTokenTestValue";
	
	private final MemberRepository memberRepository = mock(MemberRepository.class);
	private final JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
	private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final MemberService memberService = new MemberServiceImpl(memberRepository, passwordEncoder, jwtTokenProvider, authenticationManager);

	@Test
	@DisplayName("회원을 등록한다")
	public void createTest() {
		//given
		MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email.getValue(), password.getValue());
		Member member = new Member(1L, email.getValue(), password.getValue());
		doReturn(member).when(memberRepository).save(any());
		doReturn(jwtToken).when(jwtTokenProvider).createToken(any());
		doReturn(password.getValue()).when(passwordEncoder).encode(any());
		
		//when
		MemberCreateResponse memberCreateResponse = memberService.create(memberCreateRequest);
		
		//then
		assertAll(
			()->assertEquals(memberCreateResponse.getEmail(), member.getEmail()),
			()->assertEquals(memberCreateResponse.getId(), member.getId()),
			()->assertEquals(memberCreateResponse.getJwtToken(), jwtToken)
		);
	}
	
	@Test
	@DisplayName("이미 등록된 이메일로는 등록 할 수 없다.")
	public void emailduplicateCheck() {
		//given
		MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email.getValue(), password.getValue());
		doReturn(true).when(memberRepository).existsByEmail(any());
			
		//when
		ThrowingCallable callable = () -> memberService.create(memberCreateRequest);
			
		//then
		assertThatExceptionOfType(BizRuntimeException.class)
			.isThrownBy(callable)
			.withMessageMatching("이미 등록되어있는 이메일입니다.");
	}
	
	@Test
	@DisplayName("아이디 암호로 로그인 할 수 있다.")
	public void loginTest() {
		//given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(email.getValue(), password.getValue());
		Member member = new Member(1L, email.getValue(), password.getValue());

		doReturn(member).when(memberRepository).findByEmail(any());
		doReturn(jwtToken).when(jwtTokenProvider).createToken(any());
		
		//when
		MemberLoginResponse memberLoginResponse = memberService.login(memberLoginRequest);
		
		//then
		assertAll(
			()->assertEquals(memberLoginResponse.getEmail(), member.getEmail()),
			()->assertEquals(memberLoginResponse.getId(), member.getId()),
			()->assertEquals(memberLoginResponse.getJwtToken(), jwtToken)
		);
	}
}
