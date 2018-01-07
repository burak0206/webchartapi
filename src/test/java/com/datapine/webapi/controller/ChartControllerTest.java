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
public class ChartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "User", password = "user", roles = "User")
    public void shouldIsBadRequestWhenGetChartWithNoDimension() throws Exception {
        mockMvc.perform(
                post("/chart")
                        .content("{\"dimensions\": [],\"measures\":[\"champions\",\"leagues\"]}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    @WithMockUser(username = "User", password = "user", roles = "User")
    public void shouldIsBadRequestWhenGetChartWithTwoDimensions() throws Exception {
        mockMvc.perform(
                post("/chart")
                        .content("{\"dimensions\": [\"team\",\"team2\"],\"measures\":[\"champions\",\"leagues\"]}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    @WithMockUser(username = "User", password = "user", roles = "User")
    public void shouldIsBadRequestWhenGetChartWithNoMeasure() throws Exception {
        mockMvc.perform(
                post("/chart")
                        .content("{\"dimensions\": [\"team\"],\"measures\":[]}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    @WithMockUser(username = "User", password = "user", roles = "User")
    public void shouldIsBadRequestWhenGetChartWithFourMeasures() throws Exception {
        mockMvc.perform(
                post("/chart")
                        .content("{\"dimensions\": [\"team\"],\"measures\":[\"champions\",\"leagues\",\"champions\",\"leagues\"]}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    public void shouldIsWithoutUser() throws Exception {
        mockMvc.perform(
                post("/chart")
                        .content("{\"dimensions\": [\"team\"],\"measures\":[\"champions\",\"leagues\"]}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithMockUser(username = "User", password = "user", roles = "User")
    public void shouldIsOkWhenGetChartWithTwoMeasuresAndOneDimension() throws Exception {
        mockMvc.perform(
                post("/chart")
                        .content("{\"dimensions\": [\"team\"],\"measures\":[\"champions\",\"leagues\"]}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );
    }
}