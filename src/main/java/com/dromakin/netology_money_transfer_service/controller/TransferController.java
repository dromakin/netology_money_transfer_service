/*
 * File:     TransferController
 * Package:  com.dromakin.netology_money_transfer_service.controller
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 28.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.28
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.controller;

import com.dromakin.netology_money_transfer_service.dto.ConfirmOperationDTO;
import com.dromakin.netology_money_transfer_service.dto.OperationDTO;
import com.dromakin.netology_money_transfer_service.dto.OperationResponseDTO;
import com.dromakin.netology_money_transfer_service.models.Operation;
import com.dromakin.netology_money_transfer_service.service.ConfirmOperationService;
import com.dromakin.netology_money_transfer_service.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Slf4j
public class TransferController {

    private final TransferService transferService;
    private final ConfirmOperationService confirmOperationService;
    static final Logger fileLogger = LoggerFactory.getLogger("OperationsLog");

    public TransferController(TransferService transferService, ConfirmOperationService confirmOperationService) {
        this.transferService = transferService;
        this.confirmOperationService = confirmOperationService;
        fileLogger.info("Transfer Controller start!");
    }

    @PostMapping("transfer")
    public OperationResponseDTO transferMoney(@RequestBody @Valid OperationDTO operation) {
        fileLogger.info("Transfer transaction request received [{}]", operation);
        Operation result = transferService.transferMoney(operation);
        fileLogger.info("Transfer operation processed [{}]", result);
        return new OperationResponseDTO(result.getId());
    }

    @PostMapping("confirmOperation")
    public OperationResponseDTO handleRequest(@RequestBody @Valid ConfirmOperationDTO confirmOperationDTO) {
        fileLogger.info("Request for confirmation of transfer operation received [{}]", confirmOperationDTO);
        Operation result = confirmOperationService.confirm(confirmOperationDTO);
        fileLogger.info("Transaction confirmation request processed {}", result);
        return new OperationResponseDTO(result.getId());
    }

}
