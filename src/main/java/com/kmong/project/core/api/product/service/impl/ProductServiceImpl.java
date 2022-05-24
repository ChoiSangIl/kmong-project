package com.kmong.project.core.api.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmong.project.core.api.product.domain.ProductRepository;
import com.kmong.project.core.api.product.dto.request.ProductSearchRequest;
import com.kmong.project.core.api.product.dto.response.ProductSearchResponse;
import com.kmong.project.core.api.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public ProductSearchResponse search(ProductSearchRequest request) {
		return new ProductSearchResponse(productRepository.findAll());
	}
}
