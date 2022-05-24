package com.kmong.project.core.api.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kmong.project.common.domain.BaseEntity;
import com.kmong.project.core.api.product.domain.Product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "ORDER_ITEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDR_PROD_ID")
	private Long orderItemId;
	
	@ManyToOne
	@JoinColumn(name = "ORDR_NUM")
	@JsonBackReference
	Order order;
	
	@OneToOne
	@JoinColumn(name = "PROD_ID")
	private Product product;
	
	@Column(name="ORDR_PROD_PRC")
	private int orderAmountByProduct;	
	
	@Column(name="ORDR_QTY")
	private int quantity;
	
	public OrderItem(Product product, Order order, int unitPrice, int orderQuantity) {
		this.product = product;
		this.order = order;
		this.orderAmountByProduct = getOrderProductPrice(unitPrice, orderQuantity);
		this.quantity = orderQuantity;
	}
	
	private int getOrderProductPrice(int productUnitPrice, int orderQuantity) {
		return productUnitPrice * orderQuantity;
	}
	
	public void setOrder(Order order) {
		if(this.order != null) {
			this.order.getOrderItems().remove(this);
		}
		this.order = order;
		order.getOrderItems().add(this);
	}
}
