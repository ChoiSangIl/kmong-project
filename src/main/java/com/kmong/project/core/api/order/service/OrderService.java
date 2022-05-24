package com.kmong.project.core.api.order.service;

import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.request.OrderListRequest;
import com.kmong.project.core.api.order.dto.response.OrderCreateResponse;
import com.kmong.project.core.api.order.dto.response.OrderListResponse;

public interface OrderService {
	/**
	 * 주문서 생성
	 * @param orderRequest
	 * @return
	 */
	OrderCreateResponse orderProcess(OrderCreateRequest orderRequest);
	
	/**
	 * 주문 리스트 조회
	 * @param request
	 * @return
	 */
	OrderListResponse getMyOrderList(OrderListRequest request);
}
