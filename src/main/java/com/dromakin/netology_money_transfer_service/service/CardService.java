/*
 * File:     CardService
 * Package:  com.dromakin.netology_money_transfer_service.service
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 25.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.25
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.service;

import com.dromakin.netology_money_transfer_service.models.Card;
import com.dromakin.netology_money_transfer_service.repository.CardRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Validated
public class CardService {
    private static final SimpleDateFormat tillDateFormat = new SimpleDateFormat("MM/YY", Locale.ENGLISH);

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Value("${server.cards.generate.count:5}")
    private String countCards;

    @Value("${server.cards.generate.from:10/14}")
    private String tillDateFrom;

    @Value("${server.cards.generate.to:10/30}")
    private String tillDateTo;

    @EventListener(ApplicationReadyEvent.class)
    public void loadCardsToRepo() throws ParseException {
        Faker faker = new Faker();

        long smallest = 1000_0000_0000_0000L;
        long biggest = 9999_9999_9999_9999L;

        for (int i = 0; i < Integer.parseInt(countCards); i++) {
            cardRepository.create(
                    Card.builder()
                            .number(String.valueOf(ThreadLocalRandom.current().nextLong(smallest, biggest + 1)))
                            .validTill(tillDateFormat.format(faker.date().between(tillDateFormat.parse(tillDateFrom), tillDateFormat.parse(tillDateTo))))
                            .CVV(ThreadLocalRandom.current().nextInt(100, 999 + 1))
                            .build()
            );
        }

        // for test
        cardRepository.create(
                Card.builder()
                        .number("8566161424813126")
                        .validTill("10/26")
                        .CVV(126)
                        .build()
        );

        cardRepository.create(
                Card.builder()
                        .number("1248077440043895")
                        .validTill("12/24")
                        .CVV(951)
                        .build()
        );
    }

}
