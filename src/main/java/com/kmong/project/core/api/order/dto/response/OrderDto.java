package com.kmong.project.core.api.order.dto.response;

import java.util.List;

import com.kmong.project.core.api.order.domain.type.OrderStatus;
import com.kmong.project.core.api.order.dto.request.OrderProductDto;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderDto {
	private Long orderNumber;
	
	private int orderAmount;
	
	private int paymentAmount;
	
	private OrderStatus status;
	
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
