package com.kmong.project.core.api.order.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.common.exception.ErrorCode;
import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.auth.service.MemberService;
import com.kmong.project.core.api.order.domain.Order;
import com.kmong.project.core.api.order.domain.OrderRepository;
import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.request.OrderListRequest;
import com.kmong.project.core.api.order.dto.response.OrderCreateResponse;
import com.kmong.project.core.api.order.dto.response.OrderListResponse;
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
	public OrderCreateResponse orderProcess(OrderCreateRequest orderRequest) {
		Order order = Order.from(orderRequest);
		order.setMember(memberService.findByMemberFromSecurity());
		order.getOrderItems().forEach(orderItem->{
			//존재하지 않는 상품이면 오류 처리, (프론트에서 들어온 상품정보가 맞는지 체크하는 부분은 제외)
			if(!productRepository.existsById(orderItem.getProduct().getId())) {
				throw new BizRuntimeException(ErrorCode.INVALID_PRODUCT);	
			}
		});
		order = orderRepository.save(order);
		return OrderCreateResponse.of(order.getOrderNumber(), order.getOrderAmount(), order.getPaymentAmount());
	}

	@Override
	public OrderListResponse getMyOrderList(OrderListRequest request) {
		Member member = memberService.findByMemberFromSecurity();
		List<Order> orders = orderRepository.findByMember(member);
		return new OrderListResponse(orders);
	}
}
