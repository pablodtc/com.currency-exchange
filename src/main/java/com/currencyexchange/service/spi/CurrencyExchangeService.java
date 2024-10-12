package com.currencyexchange.service.spi;

import com.currencyexchange.model.CurrencyExchangeRequest;
import com.currencyexchange.model.CurrencyExchangeResponse;
import reactor.core.publisher.Mono;

public interface CurrencyExchangeService {
  Mono<CurrencyExchangeResponse> executeChange(CurrencyExchangeRequest request);
}
