package com.currencyexchange.exception.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeErrors {

  public static final String E001_CODE = "E001";
  public static final String E001_MESSAGE = "El tipo de cambio no existe";

}
