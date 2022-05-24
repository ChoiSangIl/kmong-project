package com.kmong.project.core.api.order.dto.response;

import java.util.List;

import com.kmong.project.core.api.order.domain.type.OrderStatus;
import com.kmong.project.core.api.order.dto.request.OrderProductDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderDto {
	@Schema(description = "주문번호")
	private Long orderNumber;
	
	@Schema(description = "주문금액")
	private int orderAmount;
	
	@Schema(description = "결제금액")
	private int paymentAmount;
	
	@Schema(description = "주문상태")
	private OrderStatus status;
	
	@Schema(description = "주문상품 리스트")
	List<OrderItemDto> orderItems;

	@Builder
	private OrderDto(Long orderNumber, int orderAmount, int paymentAmount, OrderStatus status, List<OrderItemDto> orderItems) {
		this.orderNumber = orderNumber;
		this.orderAmount = orderAmount;
		this.paymentAmount = paymentAmount;
		this.status = status;
		this.orderItems = orderItems;
	}
}
