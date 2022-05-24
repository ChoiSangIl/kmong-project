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
import com.kmong.project.common.exception.BizRuntimeException;
import com.kmong.project.common.exception.ErrorCode;
import com.kmong.project.core.api.auth.domain.Member;
import com.kmong.project.core.api.order.domain.type.OrderStatus;
import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.product.domain.Product;

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
	@Column(name = "ORDR_NUM")
	private Long orderNumber;
	
	@Column(name = "ORDR_AMT", nullable = false)
	private int orderAmount;
	
	@Column(name = "PAY_AMT", nullable = false)
	private int paymentAmount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@Cascade(CascadeType.PERSIST)
	List<OrderItem> orderItems = new ArrayList<OrderItem>();;
	
	@OneToOne
	@JoinColumn(name = "MEM_ID")
	@Cascade(CascadeType.PERSIST)
	private Member member;
	
	@Version
	private long version;
	
	public Order(int orderAmount, OrderStatus status) {
		this.orderAmount = orderAmount;
		this.status = status;
	}
	
	private Order(OrderCreateRequest orderRequest) {
		this.status = OrderStatus.PAYMENT_READY;
		
		//상품정보 Setting...
		orderRequest.getProducts().forEach((obj)->{
			Product product = Product.builder().productId(obj.getProductId()).build();
			OrderItem orderItem = new OrderItem(product, this, obj.getUnitPrice(), obj.getQuantity());
			this.addOrderItem(orderItem);
			this.orderAmount = this.orderAmount + orderItem.getPrice();
		});
		
		//결제금액
		this.paymentAmount = orderRequest.getPaymentAmount();
		
		//front에서 들어온 주문상품의 금액이 다를 경우 오류 처리
		if(this.orderAmount != orderRequest.getOrderAmount()) {
			throw new BizRuntimeException(ErrorCode.INVALID_ORDER_AMOUNT);
		}
		
		//결제금액이 주문금액보다 더 클 경우 오류 처리
		if(this.paymentAmount > this.orderAmount) {
			throw new BizRuntimeException(ErrorCode.INVALID_PAYMENT_AMOUNT);
		}
	}
	
	public void addOrderItem(OrderItem product) {
		orderItems.add(product);
		product.setOrder(this);
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	public static Order from(OrderCreateRequest orderRequest) {
		return new Order(orderRequest);
	}
	
	/**
	 * test function
	 */
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
}
