package com.currencyexchange.util;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExchangeRateTable {
  private static final Map<Currency, ExchangeRateModel> equivalenceTable = new HashMap<>();

  static {
    equivalenceTable.put(Currency.USD_PEN, new ExchangeRateModel("USD", "PEN", new BigDecimal("0.27")));
    equivalenceTable.put(Currency.PEN_USD, new ExchangeRateModel("PEN", "USD", new BigDecimal("3.75")));
  }
  public Mono<ExchangeRateModel> getValueByKey (Currency key) {
    return Mono.just(equivalenceTable.get(key));
  }
}
