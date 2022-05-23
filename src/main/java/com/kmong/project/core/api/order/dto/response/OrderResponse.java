package com.kmong.project.core.api.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderResponse {

	@Schema(description = "주문번호")
	private Long orderNumber;

	@Schema(description = "주문금액")
	private int orderAmount;
	
	private OrderResponse(Long orderNumber, int orderAmount) {
		this.orderNumber = orderNumber;
		this.orderAmount = orderAmount;
	}
	
	public static OrderResponse of(Long orderNumber, int orderAmount) {
		return new OrderResponse(orderNumber, orderAmount);
	}
}
