/*
 * File:     CardRepositoryImpl
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
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Validated
public class CardRepositoryImpl implements CardRepository {

    private final Map<String, Card> cards = new ConcurrentHashMap<>();

    @Override
    public void create(@NotNull @Valid Card card) {
        cards.put(card.getNumber(), card);
    }

    @Override
    public boolean delete(@NotNull @Valid Card card) {
        return cards.remove(card.getNumber(), card);
    }

    @Override
    public Optional<Card> getByNumber(String number) {
        return Optional.ofNullable(cards.get(number));
    }
}
