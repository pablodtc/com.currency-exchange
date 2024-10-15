package com.currencyexchange.web.controller;

import com.currencyexchange.exception.model.ApiResponse;
import com.currencyexchange.service.spi.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JwtController {
  private final JwtService jwtService;
  @GetMapping("/generate-token")
  public Mono<ApiResponse<String>> generateToken(@RequestParam String permissions) {
    return jwtService
        .generateToken(permissions)
        .map(jwtString -> ApiResponse.<String>builder()
        .code("A01")
        .message(jwtString)
        .build());
  }

}
