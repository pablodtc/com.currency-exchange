package com.currencyexchange.service.spi;

import reactor.core.publisher.Mono;

public interface JwtService {
  Mono<String> generateToken (String permissions);
  Mono<Boolean> validateToken (String token, String permissionToValidate);
}
