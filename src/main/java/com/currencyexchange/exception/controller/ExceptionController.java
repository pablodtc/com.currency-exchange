package com.currencyexchange.exception.controller;

import com.currencyexchange.exception.model.ApiResponse;
import com.currencyexchange.exception.model.CodeErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class ExceptionController {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Mono<ApiResponse<?>>> handleUnhandledExceptions(IllegalArgumentException error) {
    log.error("Ocurrió un error", error);
    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(CodeErrors.E001_CODE)
        .message(CodeErrors.E001_MESSAGE)
        .build();
    return new ResponseEntity<>(Mono.just(apiResponse), HttpStatus.NOT_FOUND);
  }

}
