package com.kmong.project.core.api.product.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kmong.project.core.api.product.domain.Product;
import com.kmong.project.core.api.product.domain.ProductRepository;
import com.kmong.project.core.api.product.dto.request.ProductSearchRequest;
import com.kmong.project.core.api.product.dto.response.ProductSearchResponse;
import com.kmong.project.core.api.product.service.ProductService;

@DisplayName("상품 검색 서비스 로직 테스트")
public class ProductServiceImplTest {
	ProductRepository productRepository = mock(ProductRepository.class);
	ProductService productService = new ProductServiceImpl(productRepository);
	
	@Test
	@DisplayName("상품 검색 서비스 로직 테스트")
	public void testSearch() {
		//given
		Product product = Product.builder()
							.productId(1L)
							.productKorName("test상품")
							.manufacturer("테스트업체")
							.unitPrice(1000)
							.stock(1000)
							.thumbnailUrl("xx.jpg")
							.build();
		
		ProductSearchRequest request = new ProductSearchRequest();
		List<Product> products = new ArrayList<Product>();
		products.add(product);
		doReturn(products).when(productRepository).findAll();

		//when
		ProductSearchResponse response = productService.search(request);
		
		//tthen
		assertAll(
			()->assertEquals(response.getProducts().get(0).getId(), product.getId()),
			()->assertEquals(response.getProducts().get(0).getUnitPrice(), product.getUnitPrice()),
			()->assertEquals(response.getProducts().get(0).getStock(), product.getStock()),
			()->assertEquals(response.getProducts().get(0).getProductKorName(), product.getProductKorName())
		);
		
	}

}
