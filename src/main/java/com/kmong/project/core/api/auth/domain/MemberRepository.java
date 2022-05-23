package com.kmong.project.core.api.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByEmail(Email email);
	
	Member findByEmail(Email email);
	
	Member findByEmailAndPassword(Email email, Password password);
}
