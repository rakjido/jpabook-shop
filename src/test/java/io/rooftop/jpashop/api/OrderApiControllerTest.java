package io.rooftop.jpashop.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderApiControllerTest {

    @Autowired
    OrderApiController orderApiController;

    MockMvc mockMvc;

    @Test
    public void ordersV2_테스트() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(orderApiController).build();
        // When

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/orders"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}