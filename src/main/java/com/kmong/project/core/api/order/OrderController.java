package com.kmong.project.core.api.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kmong.project.core.api.order.dto.request.OrderRequest;
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
	@Operation(summary = "주문서 생성 요청")
	public OrderResponse order(@RequestBody @Validated OrderRequest orderRequest) {
		return orderService.orderProcess(orderRequest);
	}
	
	@GetMapping("/api/v1/order")
	@Operation(summary = "주문 리스트 조회")
	public String getOrders() {
		return "test...";
	}

}
