package com.dromakin.netology_money_transfer_service.service;

import com.dromakin.netology_money_transfer_service.dto.OperationDTO;
import com.dromakin.netology_money_transfer_service.exceptions.OperationException;
import com.dromakin.netology_money_transfer_service.models.Amount;
import com.dromakin.netology_money_transfer_service.models.Status;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class TransferServiceTest {

    @Autowired
    TransferService service;
    private static final Amount correctAmount = new Amount(12345L, "RUB");
    private static final Amount amountWithUSD = new Amount(12345L, "USD");
    private static final OperationDTO correctOperation = new OperationDTO(
            "8566161424813126",
            "10/26",
            "126",
            "1248077440043895",
            correctAmount
    );

    private static final OperationDTO operationIncorrectCard = new OperationDTO(
            "1234567",
            "07/23",
            "950",
            "8566161424813126",
            correctAmount
    );

    private static final OperationDTO operationWithUSD = new OperationDTO(
            "8566161424813126",
            "10/26",
            "126",
            "1248077440043895",
            amountWithUSD
    );

    @Test
    public void transferMoneyCorrectData() {
        var operation = service.transferMoney(correctOperation);
        assertThat(operation.getCardFrom().getNumber()).isEqualTo(correctOperation.getCardFromNumber());
        assertThat(operation.getCardTo().getNumber()).isEqualTo(correctOperation.getCardToNumber());
        assertThat(operation.getStatus()).isEqualTo(Status.WAIT_APPROVE);
        assertThat(operation.getAmount().getValue()).isEqualTo(correctAmount.getValue() / 100);
        assertThat(operation.getAmount().getCurrency()).isEqualTo(correctAmount.getCurrency());
    }

    @Test
    public void transferMoneyIncorrectCard() {
        Throwable err = catchThrowable(() -> {
            service.transferMoney(operationIncorrectCard);
        });
        assertThat(err)
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(err.getMessage())
                .endsWith("Card number length is 16! Wrong format card!");
    }

    @Test
    public void transferMoneyIncorrectAmount() {
        Throwable err = catchThrowable(() -> {
            service.transferMoney(operationWithUSD);
        });
        assertThat(err)
                .isInstanceOf(OperationException.class);
        assertThat(err.getMessage())
                .endsWith("Transfers in rubles only are available!");
    }

    @Test
    public void transferMoneyExceptionNPE() {
        Throwable err = catchThrowable(() -> {
            service.transferMoney(null);
        });
        assertThat(err)
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(err.getMessage())
                .endsWith("null");
    }
}