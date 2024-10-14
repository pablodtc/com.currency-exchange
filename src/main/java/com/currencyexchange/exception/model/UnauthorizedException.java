package com.currencyexchange.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnauthorizedException extends RuntimeException {

  private String code;
  private String message;

}
