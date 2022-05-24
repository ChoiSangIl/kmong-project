package com.kmong.project.core.api.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderResponse {

	@Schema(description = "주문번호")
	private Long orderNumber;

	@Schema(description = "주문금액")
	private int orderAmount;
	
	@Schema(description = "결제금액")
	private int paymentAmount;
	
	private OrderResponse(Long orderNumber, int orderAmount, int paymentAmount) {
		this.orderNumber = orderNumber;
		this.orderAmount = orderAmount;
		this.paymentAmount = paymentAmount;
	}
	
	public static OrderResponse of(Long orderNumber1, int orderAmount2, int paymentAmount3) {
		return new OrderResponse(orderNumber1, orderAmount2, paymentAmount3);
	}
}
