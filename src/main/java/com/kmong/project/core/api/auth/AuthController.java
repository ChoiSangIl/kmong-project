package com.kmong.project.core.api.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kmong.project.core.api.auth.dto.request.MemberCreateRequest;
import com.kmong.project.core.api.auth.dto.request.MemberLoginRequest;
import com.kmong.project.core.api.auth.dto.response.MemberCreateResponse;
import com.kmong.project.core.api.auth.dto.response.MemberLoginResponse;
import com.kmong.project.core.api.auth.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/v1/auth")
@RestController
@Tag(name = "인증관련 API")
public class AuthController {
	private final MemberService memberService;
	
	@Autowired
	public AuthController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping
	@Operation(summary = "회원가입")
    @SecurityRequirements(value = {})
	private MemberCreateResponse create(@RequestBody @Validated MemberCreateRequest memeberCreateRequest) {
		return memberService.create(memeberCreateRequest);
	}
	
	@PostMapping("/login")
	@Operation(summary = "로그인")
    @SecurityRequirements(value = {}) 
	private MemberLoginResponse login(@RequestBody @Validated MemberLoginRequest memeberLoginRequest) {
		return memberService.login(memeberLoginRequest);
	}
	
	@GetMapping("/logout")
	@Operation(summary = "로그아웃")
    @SecurityRequirements(value = {}) 
	private String logout(HttpServletRequest httpServletRequest) {
		return "ok";
	}
}
