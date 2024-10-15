package com.currencyexchange.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

  public static final long JWT_DURATION = TimeUnit.MINUTES.toMillis(2);
  public static final String JWT_SIGNATURE_SECRET = "secret";
  public static final String CLAIM_PERMISSIONS = "permissions";
}
