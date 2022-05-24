package com.kmong.project.core.api.order.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.kmong.project.core.api.order.domain.Order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderListResponse {
	@Schema(description = "주문 리스트")
	List<OrderDto> orderList = new ArrayList<OrderDto>();
	
	@Schema(description = "총 주문 금액")
	int totalOrderAmount;
	
	@Schema(description = "총 결제 금액")
	int totalPaymentAmount;
	
	public OrderListResponse (List<Order> orders) {
		orders.forEach(order->{
			List<OrderItemDto> items = new ArrayList<OrderItemDto>();
			order.getOrderItems().forEach(item->{
				OrderItemDto orderItem = OrderItemDto.builder()
										.productId(item.getProduct().getId())
										.productName(item.getProduct().getProductKorName())
										.unitPrice(item.getProduct().getUnitPrice())
										.quantity(item.getQuantity())
										.orderAmountByProduct(item.getPrice())
										.build();
				items.add(orderItem);
			});
			
			OrderDto orderDto = OrderDto.builder()
								.orderNumber(order.getOrderNumber())
								.orderAmount(order.getOrderAmount())
								.paymentAmount(order.getPaymentAmount())
								.status(order.getStatus())
								.orderItems(items)
								.build();
			
			this.orderList.add(orderDto);
			this.totalOrderAmount = this.totalOrderAmount + order.getOrderAmount();
			this.totalPaymentAmount = this.totalPaymentAmount + order.getPaymentAmount();
		});
	}
}
