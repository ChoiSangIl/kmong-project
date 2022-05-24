package com.kmong.project.core.api.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kmong.project.common.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "PRODUCTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROD_ID")
	private Long id;
	
	@Column(name = "PROD_KOR_NM")
	private String productKorName;
	
	@Column(name = "MNFC")
	private String manufacturer;

	@Column(name="UNIT_PRC")
	private int unitPrice;

	@Column(name="STOCK")
	private int stock;
	
	@Column(name="THUMB_URL")
	private String thumbnailUrl;

	@Builder
	public Product(Long productId, String productKorName, String manufacturer, int unitPrice, int stock, String thumbnailUrl) {
		this.id = productId;
		this.productKorName = productKorName;
		this.manufacturer = manufacturer;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.thumbnailUrl = thumbnailUrl;
	}
}
