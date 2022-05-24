package com.kmong.project.core.api.order.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.common.exception.ErrorCode;
import com.kmong.project.core.api.auth.service.MemberService;
import com.kmong.project.core.api.order.domain.Order;
import com.kmong.project.core.api.order.domain.OrderRepository;
import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.response.OrderResponse;
import com.kmong.project.core.api.order.service.OrderService;
import com.kmong.project.core.api.product.domain.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final MemberService memberService;

	@Override
	@Transactional
	public OrderResponse orderProcess(OrderCreateRequest orderRequest) {
		Order order = Order.from(orderRequest);
		order.setMember(memberService.findByMemberFromSecurity());
		order.getOrderItems().forEach(orderItem->{
			//존재하지 않는 상품이면 오류 처리, (금액 및 데이터 정합성 검사는 제외)
			if(!productRepository.existsById(orderItem.getProduct().getId())) {
				throw new BizRuntimeException(ErrorCode.INVALID_PRODUCT);	
			}
		});
		order = orderRepository.save(order);
		return OrderResponse.of(order.getOrderNumber(), order.getOrderAmount(), order.getPaymentAmount());
	}
}
