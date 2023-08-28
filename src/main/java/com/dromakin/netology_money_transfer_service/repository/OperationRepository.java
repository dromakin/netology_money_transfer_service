/*
 * File:     OperationRepository
 * Package:  com.dromakin.netology_money_transfer_service.repository
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 21.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.21
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.repository;

import com.dromakin.netology_money_transfer_service.models.Operation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface OperationRepository {

    void create(@NotNull @Valid Operation operation);
    void update(@NotNull @Valid Operation operation);
    boolean delete(@NotNull @Valid Operation operation);
    void deleteAll();
    Optional<Operation> getById(Long id);

}
