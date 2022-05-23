package com.kmong.project.core.api.order.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.common.exception.ErrorCode;
import com.kmong.project.core.api.auth.service.MemberService;
import com.kmong.project.core.api.order.domain.Order;
import com.kmong.project.core.api.order.domain.OrderRepository;
import com.kmong.project.core.api.order.dto.request.OrderRequest;
import com.kmong.project.core.api.order.dto.response.OrderResponse;
import com.kmong.project.core.api.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final MemberService memberService;

	@Override
	@Transactional
	public OrderResponse orderProcess(OrderRequest orderRequest) {
		/*
		 * 상품데이터 정합성 검사...
		 * 재고차감...
		 */

		//주문서 생성
		Order order = Order.from(orderRequest);
		order.setMember(memberService.findByMemberFromSecurity());
		if(order.getMember() == null) {
			throw new BizRuntimeException(ErrorCode.AUTH_INVALID);
		}
		order = orderRepository.save(order);	//주문서 저장
		
		/*
		 * 결제 Process...
		 */
		return OrderResponse.of(order.getOrderNumber(), order.getOrderAmount());
	}
}
