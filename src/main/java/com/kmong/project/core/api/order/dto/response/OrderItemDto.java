package com.kmong.project.core.api.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderItemDto {
	@Schema(description = "상품 번호")
	private Long productId;

	@Schema(description = "상품명")
	private String productName;

	@Schema(description = "상품단가")
	private int unitPrice;

	@Schema(description = "구매수량")
	private int quantity;
	
	@Schema(description = "상품별주문금액")
	private int orderAmountByProduct;

	@Builder
	private OrderItemDto(Long productId, String productName, int unitPrice, int quantity, int orderAmountByProduct) {
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.orderAmountByProduct = orderAmountByProduct;
	}
}
