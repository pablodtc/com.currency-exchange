package com.currencyexchange.service.impl;

import com.currencyexchange.service.spi.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;

import static com.currencyexchange.util.Constants.*;
@Service
public class JwtServiceImpl implements JwtService {

  @Override
  public Mono<String> generateToken(String permissions) {
    Date currentDate = new Date();
    long endTime = currentDate.getTime() + JWT_DURATION;
    String jwt = Jwts.builder()
      .setIssuedAt(currentDate)
      .setExpiration(new Date(endTime))
      .signWith(SignatureAlgorithm.HS256, JWT_SIGNATURE_SECRET)
      .claim(CLAIM_PERMISSIONS, permissions)
      .compact();
    return Mono.just(jwt);
  }

  @Override
  public Mono<Boolean> validateToken(String token, String permissionToValidate) {
    Jws<Claims> parsedJwt = Jwts.parser()
      .setSigningKey(JWT_SIGNATURE_SECRET)
      .parseClaimsJws(token);
    String permissionsString = parsedJwt.getBody().get(CLAIM_PERMISSIONS, String.class);
    return Mono.just(Arrays.stream(permissionsString.split(","))
      .anyMatch(claimPermission -> permissionToValidate.equals(claimPermission)));
  }
}
