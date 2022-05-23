package com.kmong.project.core.api.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;
import com.kmong.project.core.api.auth.dto.request.MemberCreateRequest;
import com.kmong.project.core.api.auth.dto.request.MemberLoginRequest;
import com.kmong.project.core.api.auth.dto.request.MemberLogoutRequest;
import com.kmong.project.core.api.auth.dto.response.MemberCreateResponse;
import com.kmong.project.core.api.auth.dto.response.MemberLoginResponse;
import com.kmong.project.core.api.auth.dto.response.MemberLogoutResponse;
import com.kmong.project.core.api.auth.service.MemberService;

@WebMvcTest(controllers = AuthController.class, 
	excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurerAdapter.class}) 
})
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private MemberService memberService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Email email = new Email("test@kmong.co.kr");
	private static final Password password = new Password("kmongTest!23");
	private static final String jwtToken = "jwtTokenTestValue";
	

	@Test
	@DisplayName("회원가입 api 호출 테스트")
	@WithMockUser
	public void testJoin() throws JsonProcessingException, Exception {
		//given
		MemberCreateRequest memeberCreateRequest = new MemberCreateRequest(email.getValue(), password.getValue());
		MemberCreateResponse memberCreateResponse = new MemberCreateResponse(1L, email, jwtToken);
		
		doReturn(memberCreateResponse).when(memberService).create(any());
		
		//when
		mockMvc.perform(
				post("/api/v1/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memeberCreateRequest))
		        .with(csrf())
		)
		
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(1L))
		.andExpect(jsonPath("email.value").value(email.getValue()))
		.andExpect(jsonPath("jwtToken").value(jwtToken));
	}
	
	@Test
	@DisplayName("로그인 api 호출 테스트")
	@WithMockUser
	public void testLogin() throws JsonProcessingException, Exception {
		//given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(email.getValue(), password.getValue());
		MemberLoginResponse memberLoginResponse = new MemberLoginResponse(1L, email, jwtToken);

		doReturn(memberLoginResponse).when(memberService).login(any());
		
		//when
		mockMvc.perform(
				post("/api/v1/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memberLoginRequest))
				.with(csrf())
		)
		
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(1L))
		.andExpect(jsonPath("email.value").value(email.getValue()))
		.andExpect(jsonPath("jwtToken").value(jwtToken));
	}
	
	@Test
	@DisplayName("로그아웃 api 호출 테스트")
	@WithMockUser
	public void testLogout() throws JsonProcessingException, Exception {
		MemberLogoutRequest memberLogoutRequest = new MemberLogoutRequest();
		memberLogoutRequest.setAccessToken("testToken");
		
		MemberLogoutResponse response = new MemberLogoutResponse("logout 하였습니다.");
		
		doReturn(response).when(memberService).logout(any());
		
		//when
		mockMvc.perform(
				post("/api/v1/auth/logout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memberLogoutRequest))
				.with(csrf())
		)
		
		//then
		.andExpect(status().isOk());
	}

}
