/*
 * File:     OperationRepositoryImpl
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
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Validated
public class OperationRepositoryImpl implements OperationRepository {

    private final Map<Long, Operation> operations = new ConcurrentHashMap<>();

    @Override
    public void create(@NotNull @Valid Operation operation) {
        operations.put(operation.getId(), operation);
    }

    @Override
    public void update(@NotNull @Valid Operation operation) {
        delete(operation);
        create(operation);
    }

    @Override
    public boolean delete(@NotNull @Valid Operation operation) {
        return operations.remove(operation.getId(), operation);
    }

    @Override
    public void deleteAll() {
        operations.clear();
    }

    @Override
    public Optional<Operation> getById(Long id) {
        return Optional.ofNullable(operations.get(id));
    }
}
