package com.currencyexchange.web.controller;

import com.currencyexchange.model.CurrencyExchangeRequest;
import com.currencyexchange.model.CurrencyExchangeResponse;
import com.currencyexchange.service.spi.CurrencyExchangeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeControllerTest {

  @Test
  @DisplayName("Return response when exists the exchange rate")
  void executeChange() {

    CurrencyExchangeService service = Mockito.mock(CurrencyExchangeService.class);
    CurrencyExchangeController controller = new CurrencyExchangeController(service);

    CurrencyExchangeRequest request = CurrencyExchangeRequest.builder()
        .currencyOrigin("PEN")
        .currencyDestination("USD")
        .amount(new BigDecimal("10"))
        .build();

    CurrencyExchangeResponse response = CurrencyExchangeResponse.builder()
        .currencyOrigin("PEN")
        .currencyDestination("USD")
        .amount(new BigDecimal("10"))
        .changedAmount(new BigDecimal("2.7"))
        .exchangeRate(new BigDecimal("0.27"))
        .build();

    Mockito.when(service.executeChange(request)).thenReturn(Mono.just(response));

    Mono<CurrencyExchangeResponse> expectedResponse = controller.executeChange(request);

    StepVerifier.create(expectedResponse)
        .expectNextMatches(expected -> expected.getExchangeRate().compareTo(response.getExchangeRate()) == 0)
        .expectComplete()
        .verify();


  }
}