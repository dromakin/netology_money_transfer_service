/*
 * File:     OperationDTO
 * Package:  com.dromakin.netology_money_transfer_service.dto
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 23.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.23
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.dto;

import com.dromakin.netology_money_transfer_service.models.Amount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
@Builder
public class OperationDTO {
    @NotBlank
    @Size(min = 16, max = 16, message = "Card number length is 16! Wrong format card!")
    private final String cardFromNumber;

    @NotBlank
    @Pattern(regexp = "[0-1][0-9]/\\d{2}", message = "The date should be in MM/YY format")
    private final String cardFromValidTill;

    @NotBlank
    @Pattern(regexp = "\\d{3,4}", message = "There should be 3 or 4 digits")
    private final String cardFromCVV;

    @NotBlank
    @Size(min = 16, max = 16, message = "Card number length is 16! Wrong format card!")
    private final String cardToNumber;

    @Valid
    @NotNull
    private final Amount amount;
}
