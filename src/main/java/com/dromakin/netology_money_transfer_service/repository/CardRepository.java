/*
 * File:     CardRepository
 * Package:  com.dromakin.netology_money_transfer_service.repository
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 24.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.24
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.repository;

import com.dromakin.netology_money_transfer_service.models.Card;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface CardRepository {

    void create(@NotNull @Valid Card card);
    boolean delete(@NotNull @Valid Card card);
    Optional<Card> getByNumber(String number);

}
