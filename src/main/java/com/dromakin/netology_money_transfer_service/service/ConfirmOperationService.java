/*
 * File:     ConfirmOperationService
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

import com.dromakin.netology_money_transfer_service.dto.ConfirmOperationDTO;
import com.dromakin.netology_money_transfer_service.exceptions.OperationNotFoundException;
import com.dromakin.netology_money_transfer_service.models.Operation;
import com.dromakin.netology_money_transfer_service.models.Status;
import com.dromakin.netology_money_transfer_service.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Validated
@AllArgsConstructor
public class ConfirmOperationService {

    private final OperationRepository operationRepository;

    public synchronized Operation confirm(@Valid @NotNull ConfirmOperationDTO confirmOperationDTO) {

        var operation = operationRepository.getById(confirmOperationDTO.getOperationId());

        if (operation.isEmpty()) {
            throw new OperationNotFoundException("Operation processing error: no operation with id=" + confirmOperationDTO.getOperationId(), confirmOperationDTO.getOperationId());
        }

        if (!operation.get().getStatus().equals(Status.WAIT_APPROVE)) {
            throw new OperationNotFoundException("Transaction processing error: Transaction not waiting for confirmation " + confirmOperationDTO.getOperationId(), confirmOperationDTO.getOperationId());
        }
        operation.get().setCode(confirmOperationDTO.getCode());
        operation.get().setStatus(Status.DONE);
        return operation.get();
    }

}
