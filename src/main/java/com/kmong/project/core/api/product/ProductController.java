package com.kmong.project.core.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kmong.project.core.api.product.dto.request.ProductSearchRequest;
import com.kmong.project.core.api.product.dto.response.ProductSearchResponse;
import com.kmong.project.core.api.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "상품관련 API")
@RequestMapping("/api/v1")
public class ProductController {
	
	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	@Operation(summary = "상품 조회", description = "테스트상품은 자동 등록됩니다.")
	public ProductSearchResponse search(@Validated ProductSearchRequest request) {
		return productService.search(request);
	}
	
}
