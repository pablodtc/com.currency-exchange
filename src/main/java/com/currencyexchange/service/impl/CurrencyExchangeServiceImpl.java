package com.currencyexchange.service.impl;

import com.currencyexchange.model.CurrencyExchangeRequest;
import com.currencyexchange.model.CurrencyExchangeResponse;
import com.currencyexchange.service.spi.CurrencyExchangeService;
import com.currencyexchange.util.Currency;
import com.currencyexchange.util.ExchangeRateTable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
  private final ExchangeRateTable table;
  @Override
  @Cacheable(cacheNames = "currency", key = "#request.currencyOrigin + '_' + #request.currencyDestination + '_' + #request.amount")
  @CircuitBreaker(name = "currency-cb-01")
  public Mono<CurrencyExchangeResponse> executeChange(CurrencyExchangeRequest request) {

    String currencyOrigin = request.getCurrencyOrigin();
    String currencyDestination = request.getCurrencyDestination();
    Currency currency = Currency.valueOf(currencyOrigin.concat("_").concat(currencyDestination));

    return table.getValueByKey(currency).map(data -> {
      simulateSleep();
      CurrencyExchangeResponse response = new CurrencyExchangeResponse();
      response.setAmount(request.getAmount());
      response.setChangedAmount(request.getAmount().multiply(data.getExchangeRate()));
      response.setCurrencyOrigin(request.getCurrencyOrigin());
      response.setCurrencyDestination(request.getCurrencyDestination());
      response.setExchangeRate(data.getExchangeRate());

      return response;
    });
  }

  @SneakyThrows
  private void simulateSleep() {
    TimeUnit.SECONDS.sleep(5);
  }
}
