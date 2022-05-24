package com.kmong.project.core.api.product.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.kmong.project.core.api.product.domain.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductSearchResponse {
	@Schema(description = "상품 리스트")
	List<ProductDto> products = new ArrayList<ProductDto>();
	
	public ProductSearchResponse (List<Product> products) {
		products.forEach(product->{
			ProductDto productDto = ProductDto.builder()
										.id(product.getId())
										.productKorName(product.getProductKorName())
										.manufacturer(product.getManufacturer())
										.unitPrice(product.getUnitPrice())
										.stock(product.getStock())
										.thumbnailUrl(product.getThumbnailUrl())
										.build();
			this.products.add(productDto);
		});
	}
}
