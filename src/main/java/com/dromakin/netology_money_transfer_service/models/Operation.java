/*
 * File:     Operation
 * Package:  com.dromakin.netology_money_transfer_service.models
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 17.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.17
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Validated
@Builder
public class Operation {
    private final Long id;
    private final LocalDate created;

    private Card cardFrom;
    private Card cardTo;

    private Amount amount;
    private Status status;

    private Long fee;
    private String code;
    private String reason;
}
