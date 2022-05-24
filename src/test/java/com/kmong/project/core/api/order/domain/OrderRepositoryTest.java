package com.kmong.project.core.api.order.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.common.exception.ErrorCode;
import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;
import com.kmong.project.core.api.order.domain.type.OrderStatus;
import com.kmong.project.core.api.product.domain.Product;
import com.kmong.project.core.api.product.domain.ProductRepository;

@DataJpaTest
public class OrderRepositoryTest  {
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	private Order order;
	private OrderItem orderItem;
	private static final Email email = new Email("test@kmong.co.kr");
	private static final Password password = new Password("kmongTest!23");
	
	
	@BeforeEach
	@DisplayName("객체 생성")
	public void init() {
		//given
		order = new Order((int)(Math.random()*10000), OrderStatus.PAYMENT_COMPLETE);
		Product product = Product.builder().productId(1L).stock(1000).productKorName("test상품").unitPrice(1000).build();
		orderItem = new OrderItem(product, order, 1000, 10);
	}
	
	@Test
	@DisplayName("order entity 영속성 전이 Test")
	public void orderSaveTest() {
		//when
		productRepository.save(orderItem.getProduct());
		
		Member member = new Member(email.getValue(), password.getValue());
		order.addOrderItem(orderItem);
		order.setMember(member);
		
		order.getOrderItems().forEach(orderItem->{
			if(!productRepository.existsById(orderItem.getProduct().getId())) {
				throw new BizRuntimeException(ErrorCode.INVALID_PRODUCT);
			}
		});
		
		orderRepository.save(order);
		
		//then
		assertNotNull(order.getOrderNumber());
		assertNotNull(orderItem.getOrderItemId());
		assertNotNull(order.getMember());
	}
		
}
