package com.kmong.project.core.api.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kmong.project.common.domain.BaseEntity;

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
	
	@Column(name="PROD_ID")
	private Long productId;
	
	@Column(name="ORDR_PRC")
	private int price;
	
	@Column(name="ORDR_QTY")
	private int quantity;
	
	public OrderItem(Long productId, Order order, int price, int quantity) {
		this.productId = productId;
		this.order = order;
		this.price = price;
		this.quantity = quantity;
	}
	
	public void setOrder(Order order) {
		if(this.order != null) {
			this.order.getOrderProductList().remove(this);
		}
		this.order = order;
		order.getOrderProductList().add(this);
	}
}
