package com.datapine.webapi.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "Admin")
    public void shouldIsOkWhenGetStatisticsWithAdminRole() throws Exception {
        mockMvc.perform(
                post("/statistics")
                        .content("{\"last\": 5,\"timeUnit\":\"minutes\",\"mavgPoints\": 5}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(username = "User", password = "user", roles = "User")
    public void shouldIsForbiddenWhenGetStatisticsWithUserRole() throws Exception {
        mockMvc.perform(
                post("/statistics")
                        .content("{\"last\": 5,\"timeUnit\":\"minutes\",\"mavgPoints\": 5}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "Admin")
    public void shouldIsUnauthorizedWhenGetStatisticsWithInvalidTimeUnit() throws Exception {
        mockMvc.perform(
                post("/statistics")
                        .content("{\"last\": 5,\"timeUnit\":\"minutes\",\"mavgPoints\": 5}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );
    }


}