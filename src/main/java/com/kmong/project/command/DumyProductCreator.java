package com.kmong.project.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmong.project.core.api.product.domain.Product;
import com.kmong.project.core.api.product.domain.ProductRepository;

/**
 * 
 * 과제 테스트를 위한 테스트 상품 데이터를 생성한다.
 * @author Choi Sang Il
 *
 */
@Component
public class DumyProductCreator implements CommandLineRunner{

	private final ProductRepository productRepository;
	
	private static final String name = "test상품";
	private static final String thumbnailUrl = "/thumbnail/";
	private static final String manufacturer = "테스트 업체";

	@Autowired
	public DumyProductCreator(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if(productRepository.findAll().isEmpty()) {
			
			final int dumyProductCount = 20;
			List<Product> products = new ArrayList<Product>();
			
			for (int i=1; i<= dumyProductCount; i++) {
				Product product = Product.builder()
						.unitPrice(1000 * ((int)(Math.random() * 10) +1))
						.productKorName(name + i)
						.manufacturer(manufacturer + i)
						.thumbnailUrl(thumbnailUrl + i + ".jpg")
						.stock((int) (Math.random() * (10000-100+1)) + 100)
						.build();
				
				products.add(product);
			}
			
			productRepository.saveAll(products);
		}
	}

}
