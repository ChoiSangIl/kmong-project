package com.kmong.project.core.api.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmong.project.core.api.order.dto.request.OrderCreateRequest;
import com.kmong.project.core.api.order.dto.request.OrderListRequest;
import com.kmong.project.core.api.order.dto.request.OrderProductDto;
import com.kmong.project.core.api.order.dto.response.OrderCreateResponse;
import com.kmong.project.core.api.order.dto.response.OrderListResponse;
import com.kmong.project.core.api.order.dto.type.Bank;
import com.kmong.project.core.api.order.dto.type.PaymentType;
import com.kmong.project.core.api.order.service.OrderService;

@WebMvcTest(controllers = OrderController.class, 
	excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurerAdapter.class}) 
})
public class OrderControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;
	
	@MockBean
	OrderService orderService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private OrderCreateRequest orderRequest;
	private OrderCreateResponse orderResponse;
	private OrderProductDto orderProductDto;
	
	@BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .build();
        
        createOrderRequestDumyData();
    }
	
	public void createOrderRequestDumyData(){
		orderRequest = new OrderCreateRequest();
		orderResponse = OrderCreateResponse.of(1L, 1, 1000);
		orderProductDto = new OrderProductDto();
		List<OrderProductDto> orderProducts = new ArrayList<OrderProductDto>();
		orderProductDto.setProductId(1L);
		orderProductDto.setProductName("테스트상품");
		orderProductDto.setQuantity(10);
		orderProductDto.setUnitPrice(1000);
		orderProducts.add(orderProductDto);
		orderRequest.setBank(Bank.Seoul);
		orderRequest.setPaymentType(PaymentType.CARD);
		orderRequest.setCardNumber("xxxx-xxxx-xxxx-xxx");
		orderRequest.setProducts(orderProducts);
		orderRequest.setOrderAmount(10000);
		orderResponse.setOrderNumber(1L);
	}

	@Test
	@DisplayName("주문서 생성 API를 호출 할 수 있다.")
	@WithMockUser
	public void testOrder() throws JsonProcessingException, Exception {
		//given
		doReturn(orderResponse).when(orderService).orderProcess(any());
		
		//when
		mockMvc.perform(
				post("/api/v1/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderRequest))
		        .with(csrf())
		)
		
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("orderNumber").value(1));;
	}
	
	@Test
	@DisplayName("주문서 생성 API 호출 시, 요청 PARAMETER가 잘못되어 있으면 오류가 나야한다.")
	@WithMockUser
	public void testOrderParameterException() throws JsonProcessingException, Exception {
		//given
		doReturn(orderResponse).when(orderService).orderProcess(any());
		
		orderRequest.setPaymentType(null);
		orderRequest.setOrderAmount(0);
		
		//when
		mockMvc.perform(
				post("/api/v1/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderRequest))
		        .with(csrf())
		)
		
		//then
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	@DisplayName("주문서 리스트 조회 API를 호출 할 수 있다.")
	@WithMockUser
	public void testGetMyOrder() throws JsonProcessingException, Exception {
		
		OrderListRequest request = new OrderListRequest();
		
		//when
		mockMvc.perform(
				get("/api/v1/me/order")
				.contentType(MediaType.APPLICATION_JSON)
		        .with(csrf())
		)
		
		//then
		.andExpect(status().isOk());
	}


}
