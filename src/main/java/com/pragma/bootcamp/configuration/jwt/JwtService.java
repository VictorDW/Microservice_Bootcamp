package com.pragma.bootcamp.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService implements IJwtServiceAuthorization {

  @Value("${security.key.secret}")
  private String secretKey;

  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public String getSubjectFromToken(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public String getRoleFromToken(String token) {
    return extractClaim(token, claims ->  claims.get("role", String.class));
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims allClaim = extractAllClaim(token);
    return claimsResolver.apply(allClaim);
  }

  private Claims extractAllClaim(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
