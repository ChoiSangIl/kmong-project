package com.kmong.project.core.api.order.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kmong.project.core.api.auth.domain.Member;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	public List<Order> findByMember(Member member);
}
	