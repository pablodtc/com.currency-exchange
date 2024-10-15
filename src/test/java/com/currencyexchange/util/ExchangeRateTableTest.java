package com.currencyexchange.util;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateTableTest {

  @Test
  void getValueByKey() {

    ExchangeRateTable exchangeRateTable = new ExchangeRateTable();

    Mono<ExchangeRateModel> expectedResponse = exchangeRateTable.getValueByKey(Currency.PEN_USD);

    StepVerifier.create(expectedResponse)
        .expectNextMatches(expected -> expected.getExchangeRate().compareTo(new BigDecimal("3.75")) == 0)
        .expectComplete()
        .verify();
  }
}