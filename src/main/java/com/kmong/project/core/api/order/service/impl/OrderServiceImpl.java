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
import com.kmong.project.core.api.product.domain.Product;
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
			Product product = productRepository.getById(orderItem.getProduct().getId()); 
			if(product == null) {
				throw new BizRuntimeException(ErrorCode.INVALID_PRODUCT);	
			}
			
			//실제 상품과 front에서 들어온 상품정보가 다를 경우 오류 처리.
			if(orderItem.getOrderAmountByProduct() != product.getUnitPrice() * orderItem.getQuantity() ){
				throw new BizRuntimeException(ErrorCode.INVALID_PRODUCT_PRICE);	
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
