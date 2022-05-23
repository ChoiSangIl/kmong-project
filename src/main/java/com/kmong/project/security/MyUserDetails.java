package com.kmong.project.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.MemberRepository;
import com.kmong.project.core.api.auth.domain.type.Email;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Member member = memberRepository.findByEmail(new Email(username));

	 if (member == null) { throw new UsernameNotFoundException("User '" + username + "' not found"); }

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
