package com.kmong.project.core.api.auth.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kmong.project.core.api.auth.domain.type.Email;

@DataJpaTest
public class MemberRepositoryTest {

	private static final String email = "dabang@station3.co.kr";
	private static final String password = "Station3#";
	
	@Autowired
	public MemberRepository memberRepository;
	
	@Autowired
	public EntityManager em;
	
	@Test
	@DisplayName("회원 저장 및 검색")
	@Transactional
	public void saveMemberSuccess() {
		//when
		Member savedMember = memberRepository.save(new Member(email, password));
		
		//then
		assertEquals(savedMember.getId(), savedMember.getId());
		assertEquals(savedMember.getEmail().getValue(), email);
		assertEquals(savedMember.getPassword().getValue(), password);
		
		em.clear();	//실제 db에서 select 해오기 위해
		
		//when
		Optional<Member> searchMember = memberRepository.findById(savedMember.getId());
		Member member = searchMember.get();
		
		//then
		assertAll(
			()->assertEquals(member.getId(), 1L),
			()->assertEquals(member.getEmail().getValue(), email),
			()->assertEquals(member.getPassword().getValue(), password)
		);
	}
	
	@Test
	@DisplayName("회원이 존재하는지 체크한다")
	@Transactional
	public void checkExistsMemeber() {
		//when
		Boolean check = memberRepository.existsByEmail(new Email(email));
		
		//then
		assertFalse(check);
		
		//when
		memberRepository.save(new Member(email, password));
		
		em.clear();
		
		//then
		assertTrue(memberRepository.existsByEmail(new Email(email)));
	}

}
