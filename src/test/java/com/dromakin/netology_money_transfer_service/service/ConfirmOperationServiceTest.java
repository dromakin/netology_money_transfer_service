/*
 * File:     ConfirmOperationServiceTest
 * Package:  com.dromakin.netology_money_transfer_service.service
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 28.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.28
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.service;

import com.dromakin.netology_money_transfer_service.controller.TransferController;
import com.dromakin.netology_money_transfer_service.dto.ConfirmOperationDTO;
import com.dromakin.netology_money_transfer_service.exceptions.OperationNotFoundException;
import com.dromakin.netology_money_transfer_service.models.Amount;
import com.dromakin.netology_money_transfer_service.models.Card;
import com.dromakin.netology_money_transfer_service.models.Operation;
import com.dromakin.netology_money_transfer_service.models.Status;
import com.dromakin.netology_money_transfer_service.repository.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class ConfirmOperationServiceTest {

    @Autowired
    ConfirmOperationService service;

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

    private final static ConfirmOperationDTO c2cOpValid = new ConfirmOperationDTO(1L, "0123");
    private final static ConfirmOperationDTO c2cOpWithNegativeId = new ConfirmOperationDTO(-25L, "0123");
    private final static ConfirmOperationDTO c2cOpWithInvalidCodeLength = new ConfirmOperationDTO(1L, "asdfasdfasfa");
    private final static ConfirmOperationDTO c2cOpNotInRepo = new ConfirmOperationDTO(158L, "3210");

    @Test
    void confirmValidData() {
        var operation = service.confirm(c2cOpValid);
        assertThat(operation)
                .isEqualTo(testOperation);
    }

    @Test
    void confirmIdNegative() {
        Throwable err = catchThrowable(() -> {
            service.confirm(c2cOpWithNegativeId);
        });
        assertThat(err)
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(err.getMessage())
                .endsWith("Operation ID > 0");
    }

    @Test
    void confirmInvalidCode() throws Exception {
        Throwable err = catchThrowable(() -> {
            service.confirm(c2cOpWithInvalidCodeLength);
        });
        assertThat(err)
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(err.getMessage())
                .endsWith("size must be between 4 and 6");
    }

    @Test
    void confirmOperationNotInRepo() throws Exception {
        Throwable err = catchThrowable(() -> {
            service.confirm(c2cOpNotInRepo);
        });
        assertThat(err)
                .isInstanceOf(OperationNotFoundException.class);
        assertThat(err.getMessage())
                .endsWith("id=" + c2cOpNotInRepo.getOperationId());
    }

    @Test
    void confirmExceptionNPE() throws Exception {
        Throwable err = catchThrowable(() -> {
            service.confirm(null);
        });
        assertThat(err)
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(err.getMessage())
                .endsWith("null");
    }
}
