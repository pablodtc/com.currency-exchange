package com.currencyexchange.service.impl;

import com.currencyexchange.service.spi.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtServiceImpl implements JwtService {

  private static final long JWT_DURATION = TimeUnit.SECONDS.toMillis(2);
  private static final String JWT_SIGNATURE_SECRET = "secret";
  private static final String CLAIM_PERMISSIONS = "permissions";

  @Override
  public String generateToken(String permissions) {
    Date currentDate = new Date();
    long endTime = currentDate.getTime() + JWT_DURATION;
    String jwt = Jwts.builder()
      .setIssuedAt(currentDate)
      .setExpiration(new Date(endTime))
      .signWith(SignatureAlgorithm.HS256, JWT_SIGNATURE_SECRET)
      .claim(CLAIM_PERMISSIONS, permissions)
      .compact();
    return jwt;
  }

  @Override
  public boolean  validateToken(String token, String permissionToValidate) {
    Jws<Claims> parsedJwt = Jwts.parser()
      .setSigningKey(JWT_SIGNATURE_SECRET)
      .parseClaimsJws(token);
    String permissionsString = parsedJwt.getBody().get(CLAIM_PERMISSIONS, String.class);
    return Arrays.stream(permissionsString.split(","))
      .anyMatch(claimPermission -> permissionToValidate.equals(claimPermission));
  }
}
