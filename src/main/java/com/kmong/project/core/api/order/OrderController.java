package com.kmong.project.core.api.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.request.OrderListRequest;
import com.kmong.project.core.api.order.dto.response.OrderResponse;
import com.kmong.project.core.api.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "주문관련 API")
public class OrderController {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/api/v1/order")
	@Operation(summary = "상품 주문", description = "product Id 5번까지 테스트 상품 자동 등록")
	public OrderResponse order(@RequestBody @Validated OrderCreateRequest orderRequest) {
		return orderService.orderProcess(orderRequest);
	}
	
	@GetMapping("/api/v1/me/order")
	@Operation(summary = "회원 주문 내역 조회")
	public String getMyOrders(OrderListRequest request) {
		return "test...";
	}

}
