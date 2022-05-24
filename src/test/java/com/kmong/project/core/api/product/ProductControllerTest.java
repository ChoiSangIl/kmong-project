package com.kmong.project.core.api.product;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.kmong.project.core.api.product.service.ProductService;

@WebMvcTest(controllers = ProductController.class, 
excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurerAdapter.class}) 
})
@DisplayName("상품 API 컨트롤러 테스트")
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;
    
    @MockBean
	ProductService productService;
    
	@BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .build();
    }
	
	@Test
	@WithMockUser
	@DisplayName("상품검색 api를 호출 할 수 있다.")
	public void testSearch() throws Exception {
		//when
		mockMvc.perform(
				get("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
		        .with(csrf())
		)
		
		//then
		.andExpect(status().isOk());
	}

}
