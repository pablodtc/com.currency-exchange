package com.currencyexchange.service.impl;

import com.currencyexchange.model.CurrencyExchangeRequest;
import com.currencyexchange.model.CurrencyExchangeResponse;
import com.currencyexchange.service.spi.CurrencyExchangeService;
import com.currencyexchange.util.Currency;
import com.currencyexchange.util.ExchangeRateTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
  @Override
  public Mono<CurrencyExchangeResponse> executeChange(CurrencyExchangeRequest request) {

    String currencyOrigin = request.getCurrencyOrigin();
    String currencyDestination = request.getCurrencyDestination();
    Currency currency = Currency.valueOf(currencyOrigin.concat("_").concat(currencyDestination));

    return ExchangeRateTable.getValueByKey(currency).map(data -> {
      CurrencyExchangeResponse response = new CurrencyExchangeResponse();
      response.setAmount(request.getAmount());
      response.setChangedAmount(request.getAmount().multiply(data.getExchangeRate()));
      response.setCurrencyOrigin(request.getCurrencyOrigin());
      response.setCurrencyDestination(request.getCurrencyDestination());
      response.setExchangeRate(data.getExchangeRate());

      return response;
    });
  }
}
