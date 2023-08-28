package com.dromakin.netology_money_transfer_service;

import com.dromakin.netology_money_transfer_service.dto.ConfirmOperationDTO;
import com.dromakin.netology_money_transfer_service.dto.ErrorResponseDTO;
import com.dromakin.netology_money_transfer_service.dto.OperationDTO;
import com.dromakin.netology_money_transfer_service.dto.OperationResponseDTO;
import com.dromakin.netology_money_transfer_service.models.Amount;
import com.dromakin.netology_money_transfer_service.models.Card;
import com.dromakin.netology_money_transfer_service.models.Operation;
import com.dromakin.netology_money_transfer_service.models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NetologyMoneyTransferServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> moneyTransferService = new GenericContainer<>("money-transfer-service:latest:latest").withExposedPorts(5500);

    private static final OperationDTO validOperation = OperationDTO.builder()
            .cardFromNumber("8566161424813126")
            .cardFromValidTill("10/26")
            .cardFromCVV("126")
            .cardToNumber("1248077440043895")
            .amount(new Amount(12920L, "RUB"))
            .build();

    private static final OperationDTO invalidCardInOperation = OperationDTO.builder()
            .cardFromNumber("asdf")
            .cardFromValidTill("11/23")
            .cardFromCVV("157")
            .cardToNumber("1248077440043895")
            .amount(new Amount(12920L, "RUR"))
            .build();
    @Test
    void return400InvalidCard() {
        ResponseEntity<ErrorResponseDTO> response = restTemplate.postForEntity(
                "http://localhost:" + moneyTransferService.getMappedPort(5500) + "/transfer",
                invalidCardInOperation,
                ErrorResponseDTO.class);

        assertThat(response.getStatusCode().value())
                .isEqualTo(400);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                .isNotEmpty()
                .matches("\\[cardFromNumber = \\w*] reason: Card number length is 16! Wrong format card!");

    }

    @Test
    void return500NoOperationInRepo() {
        long opId = 123456789;

        ConfirmOperationDTO confirmOperationDTO = new ConfirmOperationDTO(opId, "1234");
        ResponseEntity<ErrorResponseDTO> response = restTemplate.postForEntity(
                "http://localhost:" + moneyTransferService.getMappedPort(5500) + "/confirmOperation",
                confirmOperationDTO,
                ErrorResponseDTO.class);

        assertThat(response.getStatusCode().value())
                .isEqualTo(500);

        assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                .isNotEmpty()
                .isEqualTo("Operation processing error: no operation with id=" + opId);
    }

}
