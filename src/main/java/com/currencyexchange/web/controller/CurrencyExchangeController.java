package com.currencyexchange.web.controller;

import com.currencyexchange.web.annotation.JwtAction;
import com.currencyexchange.model.CurrencyExchangeRequest;
import com.currencyexchange.model.CurrencyExchangeResponse;
import com.currencyexchange.service.spi.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CurrencyExchangeController {
  private final CurrencyExchangeService service;

  @JwtAction("change")
  @PostMapping("/change")
  public Mono<CurrencyExchangeResponse> executeChange (@RequestBody CurrencyExchangeRequest request) {

    return service.executeChange(request);
  }
}
