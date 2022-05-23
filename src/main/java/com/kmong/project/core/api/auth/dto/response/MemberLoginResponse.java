package com.kmong.project.core.api.auth.dto.response;

import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.type.Email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberLoginResponse {
	private final Long id;

	private final Email email;
	
	private final String jwtToken;

	public static MemberLoginResponse from(Member member, String jwtToken) {
        return new MemberLoginResponse(member.getId(), member.getEmail(), jwtToken);
	}
}
