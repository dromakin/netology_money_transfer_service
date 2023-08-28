/*
 * File:     TransferService
 * Package:  com.dromakin.netology_money_transfer_service.service
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 23.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.23
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.service;

import com.dromakin.netology_money_transfer_service.dto.OperationDTO;
import com.dromakin.netology_money_transfer_service.exceptions.CardException;
import com.dromakin.netology_money_transfer_service.exceptions.OperationException;
import com.dromakin.netology_money_transfer_service.models.Amount;
import com.dromakin.netology_money_transfer_service.models.Card;
import com.dromakin.netology_money_transfer_service.models.Operation;
import com.dromakin.netology_money_transfer_service.models.Status;
import com.dromakin.netology_money_transfer_service.repository.CardRepository;
import com.dromakin.netology_money_transfer_service.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Validated
@AllArgsConstructor
public class TransferService {
    private final AtomicLong operationId = new AtomicLong(0);

    private final CardRepository cardRepository;

    private final OperationRepository operationRepository;
    private static final Double FEE_PRICE = 0.01;
    private static final String CURRENCY_DEFAULT = "RUB";

    public Operation transferMoney(@Valid @NotNull OperationDTO operationDTO) {
        var operationBuilder = Operation.builder()
                .id(operationId.incrementAndGet())
                .created(LocalDate.now());

        var amountInRub = operationDTO.getAmount().getValue() / 100;
        operationBuilder = operationBuilder
                .amount(
                        Amount.builder()
                                .value(amountInRub)
                                .currency(CURRENCY_DEFAULT)
                                .build()
                )
                .fee((long) (amountInRub * FEE_PRICE));

        checkOperation(operationDTO, operationBuilder);
        operationBuilder = operationBuilder.status(Status.WAIT_APPROVE);

        Operation result = operationBuilder.build();
        operationRepository.create(result);
        return result;
    }

    private void checkOperation(OperationDTO operationDTO, Operation.OperationBuilder operation) {

        if (operationDTO.getCardFromNumber().equals(operationDTO.getCardToNumber())) {

            var err = String.format("Transaction processing error: You cannot send money to yourself [%s]", operationDTO.getCardFromNumber());
            throw new OperationException(err, operation.status(Status.ERROR).reason(err).build());

        } else {
            Optional<Card> fromCard = cardRepository.getByNumber(operationDTO.getCardFromNumber());
            Optional<Card> toCard = cardRepository.getByNumber(operationDTO.getCardToNumber());

            if (fromCard.isEmpty()) {
                var err = String.format("Transaction processing error: The card from which they are trying to transfer money does not exist [%s]", operationDTO.getCardFromNumber());
                throw new CardException(err, operation.status(Status.ERROR).reason(err).build());
            }

            if (toCard.isEmpty()) {
                var err = String.format("Transaction processing error: The card to which they are trying to transfer money does not exist [%s]", operationDTO.getCardFromNumber());
                throw new CardException(err, operation.status(Status.ERROR).reason(err).build());
            }

            operation.cardFrom(fromCard.get());
            operation.cardTo(toCard.get());
        }

        if (!CURRENCY_DEFAULT.equals(operationDTO.getAmount().getCurrency())) {

            var err = "Transaction processing error: Transfers in rubles only are available!";
            throw new OperationException(err, operation.status(Status.ERROR).reason(err).build());

        }

        String[] parts = operationDTO.getCardFromValidTill().split("/");

        // month processing
        int month = Integer.parseInt(parts[0]);

        if (month > 12 || month <= 0) {

            var err = String.format("Transaction processing error: Incorrect card validity date [%s]. The month must be in the range 1 ... 12", operationDTO.getCardFromValidTill());
            throw new OperationException(err, operation.status(Status.ERROR).reason(err).build());

        }

        // year processing
        LocalDate date = LocalDate.now();
        var cardYear = Integer.parseInt(parts[1]) + 2000;

        if ((cardYear < date.getYear()) || ((cardYear == date.getYear()) && (month < date.getMonthValue()))) {

            var err = String.format("Transaction processing error: Card validity date [%s] expired", operationDTO.getCardFromValidTill());
            throw new OperationException(err, operation.status(Status.ERROR).reason(err).build());

        }

    }
}
