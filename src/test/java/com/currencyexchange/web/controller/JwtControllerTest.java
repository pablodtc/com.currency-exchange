package com.currencyexchange.web.controller;

import com.currencyexchange.exception.model.ApiResponse;
import com.currencyexchange.service.spi.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class JwtControllerTest {

  @Test
  void generateToken() {

    JwtService jwtService = Mockito.mock(JwtService.class);
    JwtController controller = new JwtController(jwtService);
    String token = "TokenGenerado123456";
    String permission = "create";

    Mockito.when(jwtService.generateToken(Mockito.anyString())).thenReturn(Mono.just(token));

    Mono<ApiResponse<String>> expectedResponse = controller.generateToken(permission);

    StepVerifier.create(expectedResponse)
        .expectNextMatches(expected -> expected.getCode().equals("A01"))
        .expectComplete()
        .verify();


  }
}