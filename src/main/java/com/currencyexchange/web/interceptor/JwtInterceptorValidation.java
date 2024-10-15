package com.currencyexchange.web.interceptor;

import com.currencyexchange.web.annotation.JwtAction;
import com.currencyexchange.exception.model.UnauthorizedException;
import com.currencyexchange.service.spi.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptorValidation implements WebFilter {
  private final RequestMappingHandlerMapping handlerMapping;
  private final JwtService jwtService;

  @Override
  @SneakyThrows
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    HandlerMethod handlerMethod = (HandlerMethod) handlerMapping.getHandler(exchange).toFuture().get();;

    JwtAction jwtAction = handlerMethod.getMethodAnnotation(JwtAction.class);

    if (jwtAction == null) {
      return chain.filter(exchange);
    }
    String authorization = exchange.getRequest().getHeaders().get("Authorization").get(0);
    if (authorization == null || !authorization.startsWith("Bearer ")) {
      throw new UnauthorizedException("E002", "No está autorizado para consumir este servicio");
    }
    String jwt = authorization.substring(7);
    log.info("Token: {} | Action: {}", jwt, jwtAction.value());

    jwtService
        .validateToken(jwt, jwtAction.value())
        .map(isAuthorized -> {
      if (!isAuthorized) {
        throw new UnauthorizedException("E002", "No está autorizado para consumir este servicio");
      }
      return chain.filter(exchange);
    });

    return chain.filter(exchange);
  }
}
