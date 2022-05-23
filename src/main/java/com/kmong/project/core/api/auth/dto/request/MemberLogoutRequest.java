package com.kmong.project.core.api.auth.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MemberLogoutRequest {
	@NotEmpty(message = "잘못된 요청입니다.")
    private String accessToken;
}
