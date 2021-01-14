package io.rooftop.jpashop.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberApiControllerTest {
    @Autowired
    MemberApiController memberApiController;

    private MockMvc mockMvc;

    @Test
    public void memberV2_테스트() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(memberApiController).build();
        // When

        // Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v2/members"))
                    .andDo(print())
                    .andExpect(status().isOk());
    }

}