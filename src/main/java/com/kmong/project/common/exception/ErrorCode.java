package com.kmong.project.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode  {

	INTERNAL_SERVER("E001", "내부 서비스 문제가 발생했습니다. 관리자에게 문의해주세요."),
	AUTH_INVALID("E002", "로그인정보가 만료되었거나, 아이디암호가 일치하지 않습니다."),
	DUPLICATE_EMAIL("E003", "이미 등록되어있는 이메일입니다."),
	NOT_READABLE("E004", "데이터 형식을 확인해주세요."),
	INVALID_PRODUCT("E005", "상품 데이터가 존재하지 않습니다."),
	INVALID_ORDER_AMOUNT("E006", "주문금액이 맞지 않습니다."),
	INVALID_PAYMENT_AMOUNT("E007", "주문금액보다 결제금액이 더 큽니다."),
	INVALID_PRODUCT_PRICE("E008", "상품 금액 정보가 일치하지 않습니다.");

	private String code;
	private String message;

	ErrorCode(String code, String message) {
		this.message = message;
		this.code = code;
	}
}
