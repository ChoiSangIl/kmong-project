package com.kmong.project.core.api.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.request.OrderListRequest;
import com.kmong.project.core.api.order.dto.response.OrderListResponse;
import com.kmong.project.core.api.order.dto.response.OrderCreateResponse;
import com.kmong.project.core.api.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "주문관련 API")
@RequestMapping("/api/v1")
public class OrderController {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/order")
	@Operation(summary = "상품 주문", 
			description = "실제 상품 데이터와 금액이 일치하지 않으면 예외가 발생 합니다(E008) - 상품조회 api를 통해 정확한 값으로 넣어주세요.<br/> "
						+ "주문금액보다 결제 금액이 크면 예외가 발생합니다(E007) <br/>"
						+ "주문금액이 맞지 않으면 예외가 발생합니다(E006) - 상품단가*상품수량=주문금액(orderAmount) <br/>"
						+ "상품 재고와 프론트에서 들어온 상품명은 체크하지 않습니다"
						
	)
	public OrderCreateResponse order(@RequestBody @Validated OrderCreateRequest orderRequest) {
		return orderService.orderProcess(orderRequest);
	}
	
	@GetMapping("/me/order")
	@Operation(summary = "회원 주문 내역 조회")
	public OrderListResponse getMyOrders(OrderListRequest request) {
		return orderService.getMyOrderList(request);
	}

}
