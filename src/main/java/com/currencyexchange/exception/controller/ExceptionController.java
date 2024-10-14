package com.currencyexchange.exception.controller;

import com.currencyexchange.exception.model.ApiResponse;
import com.currencyexchange.exception.model.CodeErrors;
import com.currencyexchange.exception.model.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
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
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Mono<ApiResponse<?>>> handleException(RuntimeException unauthorizedException) {
    log.error("Ocurrió un error", unauthorizedException);
    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code("E002")
        .message(unauthorizedException.getMessage())
        .build();
    return new ResponseEntity<>(Mono.just(apiResponse), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ApiResponse<?>> handleExpiredJwtException(ExpiredJwtException expiredJwtException) {
    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code("E003")
        .message("El JWT está expirado")
        .build();
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }

}
