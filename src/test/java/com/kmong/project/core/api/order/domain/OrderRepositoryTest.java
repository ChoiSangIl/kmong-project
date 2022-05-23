package com.kmong.project.core.api.order.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;
import com.kmong.project.core.api.order.domain.type.OrderStatus;

@DataJpaTest
public class OrderRepositoryTest  {
	@Autowired
	OrderRepository orderRepository;
	
	private Order order;
	private OrderItem orderProduct;
	private static final Email email = new Email("test@kmong.co.kr");
	private static final Password password = new Password("kmongTest!23");
	
	
	@BeforeEach
	@DisplayName("객체 생성")
	public void init() {
		//given
		order = new Order((int)(Math.random()*10000), OrderStatus.PAYMENT_COMPLETE);
		orderProduct = new OrderItem(1L, order, 1000, 10);
	}
	
	@Test
	@DisplayName("order entity 영속성 전이 Test")
	public void orderSaveTest() {
		//when
		Member member = new Member(email.getValue(), password.getValue());
		order.addProduct(orderProduct);
		order.setMember(member);
		orderRepository.save(order);
		
		//then
		assertNotNull(order.getOrderNumber());
		assertNotNull(orderProduct.getOrderItemId());
		assertNotNull(order.getMember());
	}
		
}
