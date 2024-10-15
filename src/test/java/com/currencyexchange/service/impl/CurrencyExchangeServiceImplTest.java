package com.currencyexchange.service.impl;

import com.currencyexchange.model.CurrencyExchangeRequest;
import com.currencyexchange.model.CurrencyExchangeResponse;
import com.currencyexchange.service.spi.CurrencyExchangeService;
import com.currencyexchange.util.Currency;
import com.currencyexchange.util.ExchangeRateModel;
import com.currencyexchange.util.ExchangeRateTable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeServiceImplTest {

  @Test
  void executeChange() {

    ExchangeRateTable table = Mockito.mock(ExchangeRateTable.class);
    CurrencyExchangeService service = new CurrencyExchangeServiceImpl(table);

    Currency currency = Currency.valueOf("PEN_USD");

    ExchangeRateModel model = ExchangeRateModel.builder()
        .currencyOrigin("PEN")
        .currencyDestination("USD")
        .exchangeRate(new BigDecimal("0.27"))
        .build();

    CurrencyExchangeRequest request = CurrencyExchangeRequest.builder()
        .currencyOrigin("PEN")
        .currencyDestination("USD")
        .amount(new BigDecimal("10"))
        .build();

    Mockito.when(table.getValueByKey(currency)).thenReturn(Mono.just(model));

    Mono<CurrencyExchangeResponse> expectedResponse = service.executeChange(request);

    StepVerifier.create(expectedResponse)
        .expectNextMatches(expected -> expected.getChangedAmount().compareTo(new BigDecimal("2.7")) == 0)
        .expectComplete()
        .verify();
  }
}