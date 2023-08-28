/*
 * File:     OperationResponseDTO
 * Package:  com.dromakin.netology_money_transfer_service.dto
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 28.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.28
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Data
@Builder
public class OperationResponseDTO {
    @Min(value = 0, message = "Operation ID > 0")
    private final Long operationId;
}
