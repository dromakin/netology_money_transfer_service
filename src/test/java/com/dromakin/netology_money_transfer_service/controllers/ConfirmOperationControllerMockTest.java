/*
 * File:     ConfirmOperationControllerMockTest
 * Package:  com.dromakin.netology_money_transfer_service.controllers
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 28.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.28
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.controllers;

import com.dromakin.netology_money_transfer_service.models.Amount;
import com.dromakin.netology_money_transfer_service.models.Card;
import com.dromakin.netology_money_transfer_service.models.Operation;
import com.dromakin.netology_money_transfer_service.models.Status;
import com.dromakin.netology_money_transfer_service.repository.OperationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConfirmOperationControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OperationRepository operationRepository;

    private static Operation testOperation;

    @BeforeEach
    void addData() {
        Card cardFrom = new Card("8566161424813126", "10/26", 126);
        Card cardTo = new Card("1248077440043895", "12/24", 951);

        testOperation = Operation.builder()
                .id(1L)
                .status(Status.WAIT_APPROVE)
                .cardFrom(cardFrom)
                .cardTo(cardTo)
                .code("0123")
                .amount(Amount.builder().value(5000L).currency("RUB").build())
                .build();

        Mockito.when(operationRepository.getById(1L)).thenReturn(Optional.of(testOperation));
    }

    @Test
    void returnOk() throws Exception {
        String requestBody = "{\n" +
                             "  \"code\": \"0123\",\n" +
                             "  \"operationId\": \"1\"\n" +
                             "}\n";
        mockMvc.perform(
                        post("/confirmOperation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationId").value(1));
        Assertions.assertEquals(Status.DONE, testOperation.getStatus());
    }


    @Test
    void return400IdNegative() throws Exception {
        String requestBody = "{\n" +
                             "  \"code\": \"0123\",\n" +
                             "  \"operationId\": \"-5\"\n" +
                             "}\n";
        mockMvc.perform(
                        post("/confirmOperation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.operationId").value(-1))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IdIsString() throws Exception {
        String requestBody = "{\n" +
                             "  \"code\": \"0123\",\n" +
                             "  \"operationId\": \"AAA\"\n" +
                             "}\n";
        mockMvc.perform(
                        post("/confirmOperation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.operationId").value(-1))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }


    @Test
    void return400IdIsDouble() throws Exception {
        String requestBody = "{\n" +
                             "  \"code\": \"0123\",\n" +
                             "  \"operationId\": \"13.5\"\n" +
                             "}\n";
        mockMvc.perform(
                        post("/confirmOperation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.operationId").value(-1))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }


    @Test
    void return500NotInRepo() throws Exception {
        String requestBody = "{\n" +
                             "  \"code\": \"0123\",\n" +
                             "  \"operationId\": \"1251000\"\n" +
                             "}\n";
        mockMvc.perform(
                        post("/confirmOperation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(500))
                .andExpect(jsonPath("$.operationId").value(1251000))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }


}
