package com.currencyexchange.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Currency {

  PEN_USD,
  USD_PEN;

  private String value;

}
