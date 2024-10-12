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
public class CurrencyExchangeRequest {

  private BigDecimal amount;
  private String currencyOrigin;
  private String currencyDestination;

}
