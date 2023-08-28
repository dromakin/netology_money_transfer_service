/*
 * File:     Card
 * Package:  com.dromakin.netology_money_transfer_service.models
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 21.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.21
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
public class Card {
    @NotBlank
    @Size(min = 16, max = 16, message = "Card number length is 16!")
    String number;
    @NotBlank
    String validTill;
    @Min(value = 3, message = "CVV length > 3")
    Integer CVV;
}
