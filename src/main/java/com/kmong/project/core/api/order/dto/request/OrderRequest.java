package com.kmong.project.core.api.order.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.kmong.project.core.api.order.dto.type.Bank;
import com.kmong.project.core.api.order.dto.type.PaymentType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 주문 요청 Object
 * @author Choi Sang Il
 *
 */
@Data
public class OrderRequest {
	@Schema(description = "주문 상품 리스트")
    @NotNull(message="상품은 필수 값 입니다.")
	@Valid
	private List<OrderProductDto> products;

	@Schema(description = "결제종류")
    @NotNull(message="결제종류는 필수 값 입니다.")
	private PaymentType paymentType;
	
	@Schema(description = "결제은행")
	private Bank bank;
	
	@Schema(description = "카드번호", example = "1234-xxxx-xxxx-345")
	private String cardNumber;
	
	@Schema(description = "결제금액", example = "10000")
	@Min(1)
	private int paymentAmount;
}
