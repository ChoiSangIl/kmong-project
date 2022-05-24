package com.kmong.project.core.api.product.service;

import com.kmong.project.core.api.product.dto.request.ProductSearchRequest;
import com.kmong.project.core.api.product.dto.response.ProductSearchResponse;

public interface ProductService {
	ProductSearchResponse search(ProductSearchRequest request);
}
