/*
 * File:     OperationException
 * Package:  com.dromakin.netology_money_transfer_service.exceptions
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 24.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.24
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.exceptions;

import com.dromakin.netology_money_transfer_service.models.Operation;

public class OperationException extends RuntimeException {
    private final transient Operation operation;

    public OperationException(String message, Operation operation) {
        super(message);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
