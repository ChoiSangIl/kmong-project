package com.kmong.project.core.api.order.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;
import com.kmong.project.core.api.auth.service.MemberService;
import com.kmong.project.core.api.order.domain.Order;
import com.kmong.project.core.api.order.domain.OrderRepository;
import com.kmong.project.core.api.order.dto.request.OrderProductDto;
import com.kmong.project.core.api.order.dto.request.OrderRequest;
import com.kmong.project.core.api.order.dto.response.OrderResponse;
import com.kmong.project.core.api.order.dto.type.Bank;
import com.kmong.project.core.api.order.dto.type.PaymentType;
import com.kmong.project.core.api.order.service.OrderService;


public class OrderServiceImplTest {

	private OrderRepository orderRepository = mock(OrderRepository.class);
	private MemberService memberService = mock(MemberService.class);
	private OrderService orderService = new OrderServiceImpl(orderRepository, memberService);
	
	private OrderRequest orderRequest;
	private OrderProductDto orderProductDto;
	private static final Email email = new Email("test@kmong.co.kr");
	private static final Password password = new Password("kmongTest!23");

	@BeforeEach
	public void init() throws URISyntaxException {
		createOrderRequestDumyData();
	}

	public void createOrderRequestDumyData() {
		orderRequest = new OrderRequest();
		orderProductDto = new OrderProductDto();
		List<OrderProductDto> orderProducts = new ArrayList<OrderProductDto>();
		orderProductDto.setProductId(1L);
		orderProductDto.setProductName("테스트상품");
		orderProductDto.setQuantity(10);
		orderProductDto.setUnitPrice(1000);
		orderProducts.add(orderProductDto);
		orderRequest.setBank(Bank.Seoul);
		orderRequest.setPaymentType(PaymentType.CARD);
		orderRequest.setCardNumber("xxxx-xxxx-xxxx-xxx");
		orderRequest.setProducts(orderProducts);
		orderRequest.setPaymentAmount(10000);
	}
	
	@Test
	@DisplayName("주문서 생성 process test")
	public void orderProcessTest() {
		//given
		Order order = Order.from(orderRequest);
		order.setOrderNumber(1L);
		Member member = new Member(1L, email.getValue(), password.getValue());
		
		doReturn(order).when(orderRepository).save(any());
		doReturn(member).when(memberService).findByMemberFromSecurity();
		
		//when
		OrderResponse response = orderService.orderProcess(orderRequest);
		
		//then
		assertAll(
			()->assertEquals(response.getOrderAmount(), order.getOrderAmount()),
			()->assertEquals(response.getOrderNumber(), order.getOrderNumber())
		);
	}

}
