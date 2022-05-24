package com.kmong.project.core.api.order.service;

import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.response.OrderResponse;

public interface OrderService {
	OrderResponse orderProcess(OrderCreateRequest orderRequest);
}
