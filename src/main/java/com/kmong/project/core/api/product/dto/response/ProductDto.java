package com.kmong.project.core.api.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductDto {
	@Schema(description = "상품코드")
	private Long id;
	
	@Schema(description = "상품명")
	private String productKorName;

	@Schema(description = "제조사")
	private String manufacturer;

	@Schema(description = "상품단가")
	private int unitPrice;

	@Schema(description = "재고")
	private int stock;
	
	@Schema(description = "섬네일 url")
	private String thumbnailUrl;

	@Builder
	private ProductDto(Long id, String productKorName, String manufacturer, int unitPrice, int stock, String thumbnailUrl) {
		this.id = id;
		this.productKorName = productKorName;
		this.manufacturer = manufacturer;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.thumbnailUrl = thumbnailUrl;
	}
	
	
}
