package com.kmong.project.core.api.auth.dto.response;

import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.type.Email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberCreateResponse {
	private final Long id;

	private final Email email;
	
	private final String jwtToken;


	public static MemberCreateResponse from(Member member, String jwtToken) {
        return new MemberCreateResponse(member.getId(), member.getEmail(), jwtToken);
	}
}
