package com.kmong.project.core.api.order.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.kmong.project.common.domain.BaseEntity;
import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.order.domain.type.OrderStatus;
import com.kmong.project.core.api.order.dto.request.OrderRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="ORDERS")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_NUMBER")
	private Long orderNumber;
	
	@Column(name = "ORDER_AMOUNT", nullable = false)
	private int orderAmount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@Cascade(CascadeType.PERSIST)
	List<OrderItem> orderProductList = new ArrayList<OrderItem>();;
	
	@OneToOne
	@JoinColumn(name = "MEMBER_ID")
	@Cascade(CascadeType.PERSIST)
	private Member member;
	
	@Version
	private long version;
	
	public Order(int orderAmount, OrderStatus status) {
		this.orderAmount = orderAmount;
		this.status = status;
	}
	
	private Order(OrderRequest orderRequest) {
		this.orderAmount = orderRequest.getPaymentAmount();
		this.status = OrderStatus.PAYMENT_READY;
		orderRequest.getProducts().forEach((obj)->{
			OrderItem product = new OrderItem(obj.getProductId(), this, obj.getUnitPrice(), obj.getQuantity());
			this.addProduct(product);
		});
	}
	
	public void addProduct(OrderItem product) {
		orderProductList.add(product);
		product.setOrder(this);
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	public static Order from(OrderRequest orderRequest) {
		return new Order(orderRequest);
	}
	
	/**
	 * test function
	 */
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
}
