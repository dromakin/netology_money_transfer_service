/*
 * File:     Amount
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class Amount {
    @Min(value = 0, message = "Transfer money > 0")
    private Long value;
    @NotBlank
    @Size(min = 1, max = 3, message = "Transfer currency name 0 < length < 4")
    private String currency;
}
