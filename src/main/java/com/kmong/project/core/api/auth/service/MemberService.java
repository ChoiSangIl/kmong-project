package com.kmong.project.core.api.auth.service;

import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.dto.request.MemberCreateRequest;
import com.kmong.project.core.api.auth.dto.request.MemberLoginRequest;
import com.kmong.project.core.api.auth.dto.request.MemberLogoutRequest;
import com.kmong.project.core.api.auth.dto.response.MemberCreateResponse;
import com.kmong.project.core.api.auth.dto.response.MemberLoginResponse;
import com.kmong.project.core.api.auth.dto.response.MemberLogoutResponse;

public interface MemberService {	
	/**
	 * 회원 가입
	 * @param memberCreateRequest
	 * @return
	 */
	public MemberCreateResponse create(MemberCreateRequest memberCreateRequest);
	
	/**
	 * 로그인
	 * @param memberLoginRequest
	 * @return
	 */
	public MemberLoginResponse login(MemberLoginRequest memberLoginRequest);
	
	/**
	 * Security정보로 부터 회원을 찾는다.
	 * @return
	 */
	public Member findByMemberFromSecurity();
	
	/**
	 * 로그아웃
	 * @return
	 */
	public MemberLogoutResponse logout(MemberLogoutRequest memberLogoutRequest);
}
