/*
 * File:     OperationNotFoundException
 * Package:  com.dromakin.netology_money_transfer_service.exceptions
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 28.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.28
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.exceptions;

public class OperationNotFoundException extends RuntimeException {
    private final Long id;

    public OperationNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
