package com.kmong.project.core.api.auth.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.common.exception.ErrorCode;
import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.MemberRepository;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.dto.request.MemberCreateRequest;
import com.kmong.project.core.api.auth.dto.request.MemberLoginRequest;
import com.kmong.project.core.api.auth.dto.response.MemberCreateResponse;
import com.kmong.project.core.api.auth.dto.response.MemberLoginResponse;
import com.kmong.project.core.api.auth.service.MemberService;
import com.kmong.project.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public MemberCreateResponse create(MemberCreateRequest memberCreateRequest) {
		if (memberRepository.existsByEmail(new Email(memberCreateRequest.getEmail()))) {
			throw new BizRuntimeException(ErrorCode.DUPLICATE_EMAIL);
		}

		passwordValidation(memberCreateRequest.getPassword());

		memberCreateRequest.passwordEncryption(passwordEncoder.encode(memberCreateRequest.getPassword()));
		Member saveMember = memberRepository.save(memberCreateRequest.toMemeber());
		return MemberCreateResponse.from(saveMember, jwtTokenProvider.createToken(saveMember.getEmail().getValue()));
	}

	@Override
	public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(),
					memberLoginRequest.getPassword()));
			return MemberLoginResponse.from(memberRepository.findByEmail(new Email(memberLoginRequest.getEmail())),
					jwtTokenProvider.createToken(memberLoginRequest.getEmail()));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			throw new BizRuntimeException(ErrorCode.AUTH_INVALID);
		}
	}

	public void passwordValidation(String password) {
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\\S+$).{8,}$");
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			throw new BizRuntimeException(ErrorCode.PASSWORD_INVALID);
		}
	}

	@Override
	public Member findByMemberFromSecurity() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		String username = userDetails.getUsername();
		System.out.println(username);
		return memberRepository.findByEmail(new Email(username));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Member member = memberRepository.findByEmail(new Email(username));

		if (member == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		return org.springframework.security.core.userdetails.User//
				.withUsername(username)//
				.password(member.getPassword().getValue())//
				.authorities("USER")//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	}

}
