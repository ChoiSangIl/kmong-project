package com.kmong.project.core.api.order.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.domain.type.Email;
import com.kmong.project.core.api.auth.domain.type.Password;
import com.kmong.project.core.api.auth.service.MemberService;
import com.kmong.project.core.api.order.domain.Order;
import com.kmong.project.core.api.order.domain.OrderRepository;
import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.request.OrderListRequest;
import com.kmong.project.core.api.order.dto.request.OrderProductDto;
import com.kmong.project.core.api.order.dto.response.OrderCreateResponse;
import com.kmong.project.core.api.order.dto.response.OrderListResponse;
import com.kmong.project.core.api.order.dto.type.Bank;
import com.kmong.project.core.api.order.dto.type.PaymentType;
import com.kmong.project.core.api.order.service.OrderService;
import com.kmong.project.core.api.product.domain.ProductRepository;


public class OrderServiceImplTest {

	private OrderRepository orderRepository = mock(OrderRepository.class);
	private ProductRepository productRepository = mock(ProductRepository.class);
	private MemberService memberService = mock(MemberService.class);
	private OrderService orderService = new OrderServiceImpl(orderRepository, productRepository, memberService);
	
	private OrderCreateRequest orderRequest;
	private OrderProductDto orderProductDto;
	private static final Email email = new Email("test@kmong.co.kr");
	private static final Password password = new Password("kmongTest!23");

	@BeforeEach
	public void init() throws URISyntaxException {
		createOrderRequestDumyData();
	}

	public void createOrderRequestDumyData() {
		orderRequest = new OrderCreateRequest();
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
		orderRequest.setOrderAmount(10000);
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
		doReturn(true).when(productRepository).existsById(any());
		
		//when
		OrderCreateResponse response = orderService.orderProcess(orderRequest);
		
		//then
		assertAll(
			()->assertEquals(response.getOrderAmount(), order.getOrderAmount()),
			()->assertEquals(response.getOrderAmount(), order.getPaymentAmount()),
			()->assertEquals(response.getOrderNumber(), order.getOrderNumber())
		);
	}
	
	@Test
	@DisplayName("주문서를 생성하는데 상품이 존재하지 않으면 오류가 나야 한다.")
	public void orderProcessErrorTest() {
		//given
		Order order = Order.from(orderRequest);
		order.setOrderNumber(1L);
		Member member = new Member(1L, email.getValue(), password.getValue());
		
		doReturn(order).when(orderRepository).save(any());
		doReturn(member).when(memberService).findByMemberFromSecurity();
		doReturn(false).when(productRepository).existsById(any());
		
		//when
		Assertions.assertThrows(BizRuntimeException.class, () -> {
			orderService.orderProcess(orderRequest);
	    });
	}
	
	@Test
	@DisplayName("주문서를 생성하는데 주문금액과 상품상세 주문금액 합계가 맞지 않으면 오류가 나야한다.")
	public void orderProcessErrorTest2() {
		//given
		orderRequest.setOrderAmount(100);
		
		//when
		Assertions.assertThrows(BizRuntimeException.class, () -> {
			Order.from(orderRequest);
	    });
	}
	
	@Test
	@DisplayName("결제금액이 주문금액보다 더 크면 오류가 나야한다.")
	public void orderProcessErrorTest3() {
		//given
		orderRequest.setOrderAmount(100);
		orderRequest.setPaymentAmount(1000);
		
		//when
		Assertions.assertThrows(BizRuntimeException.class, () -> {
			Order.from(orderRequest);
	    });
	}

	@Test
	@DisplayName("주문서 가져오기 서비스 테스트")
	public void testGetMyOrderList() {
		//given
		Order order = Order.from(orderRequest);
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		Member member = new Member(1L, email.getValue(), password.getValue());
		OrderListRequest request = new OrderListRequest();

		doReturn(member).when(memberService).findByMemberFromSecurity();
		doReturn(orders).when(orderRepository).findByMember(any());
		
		//when
		OrderListResponse response = orderService.getMyOrderList(request);
		
		//then
		assertAll(
			()->assertEquals(response.getTotalOrderAmount(), order.getOrderAmount())
		);
	}
}
