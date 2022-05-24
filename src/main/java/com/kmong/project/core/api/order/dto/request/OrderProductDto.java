package com.kmong.project.core.api.order.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderProductDto {

	@Schema(description = "상품 번호", example = "1")
	@NotNull(message = "상품 id가 존재하지 않습니다.")
	private Long productId;

	@Schema(description = "상품명", example = "test 상품1")
	@NotEmpty(message = "상품명이 존재하지 않습니다.")
	private String productName;

	@Schema(description = "상품단가", example = "1000")
	@Min(1)
	private int unitPrice;

	@Schema(description = "구매수량", example = "10")
	@Min(1)
	private int quantity;

}
