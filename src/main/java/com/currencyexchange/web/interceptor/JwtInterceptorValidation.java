package com.currencyexchange.web.interceptor;

import com.currencyexchange.JwtAction;
import com.currencyexchange.service.spi.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptorValidation implements WebFilter {
  private final RequestMappingHandlerMapping handlerMapping;
  private final JwtService jwtService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    HandlerMethod handlerMethod = null;
    try {
      handlerMethod = (HandlerMethod) handlerMapping.getHandler(exchange).toFuture().get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    JwtAction jwtAction = handlerMethod.getMethodAnnotation(JwtAction.class);
    exchange.getRequest().getHeaders().get("Authorization").get(0);
    if (jwtAction == null) {
      return chain.filter(exchange);
    }
    String authorization = exchange.getRequest().getHeaders().get("Authorization").get(0);
    if (authorization == null || !authorization.startsWith("Bearer ")) {
      throw new RuntimeException("No está autorizado para consumir este servicio");
    }
    String jwt = authorization.substring(7);
    log.info("Token: {} | Action: {}", jwt, jwtAction.value());
    boolean isAuthorized = jwtService.validateToken(jwt, jwtAction.value());
    if (!isAuthorized) {
      throw new RuntimeException("No está autorizado para consumir este servicio");
    }
    return chain.filter(exchange);
  }
}
