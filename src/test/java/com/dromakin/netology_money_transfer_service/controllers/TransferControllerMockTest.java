/*
 * File:     TransferControllerMockTest
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

import com.dromakin.netology_money_transfer_service.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OperationRepository operationRepository;

    @Test
    void return200CorrectData() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 12920\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk());
    }

    @Test
    void return400IncorrectCardFromNumberTest() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"321\",\n" +
                             "  \"cardToNumber\": \"8566161424813126\",\n" +
                             "  \"cardFromCVV\": \"157\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 3500000\n" +
                             "  }\n" +
                             "}\n";
        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400OverOneIncorrectField() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"321\",\n" +
                             "  \"cardToNumber\": \"321\",\n" +
                             "  \"cardFromCVV\": \"zz\",\n" +
                             "  \"cardFromValidTill\": \"11/15\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 300000\n" +
                             "  }\n" +
                             "}\n";
        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IncorrectCardToNumberTest() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"321\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IncorrectCVVNumberTestTwoDigits() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"03\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";
        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IncorrectCVVNumberTest5letters() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"03\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IncorrectTillSomeText() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"asdfsad\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IncorrectTillMonthOver12() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"13/27\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IncorrectTillMonth0() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"0/25\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400IncorrectTillExpireDate() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"04/23\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400_amount_incorrectCurrency() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"23423423423423426\",\n" +
                             "    \"value\": 35000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400AmountNegativeValue() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"23423423423423426\",\n" +
                             "    \"value\": -50000\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400AmountValueIsString() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"1248077440043895\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": \"asf\"\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void return400TransferHimself() throws Exception {
        String requestBody = "{\n" +
                             "  \"cardFromNumber\": \"8566161424813126\",\n" +
                             "  \"cardToNumber\": \"8566161424813126\",\n" +
                             "  \"cardFromCVV\": \"126\",\n" +
                             "  \"cardFromValidTill\": \"10/26\",\n" +
                             "  \"amount\": {\n" +
                             "    \"currency\": \"RUB\",\n" +
                             "    \"value\": \"50000\"\n" +
                             "  }\n" +
                             "}\n";

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
}
