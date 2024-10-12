package com.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchangeResponse {

  private BigDecimal amount;
  private BigDecimal changedAmount;
  private String currencyOrigin;
  private String currencyDestination;
  private BigDecimal exchangeRate;

}
