package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
  private static final String JWT_SECRET = "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF";

  private SecretKey getSignKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
  }

  public String generateToken(String username) {
    return Jwts.builder()
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(getSignKey(), Jwts.SIG.HS256)
            .compact();
  }

  public String exactUsername(String token) {
    return exactClaim(token, Claims::getSubject);
  }


  public boolean validateToken(String token, UserDetails userDetails) {
    return exactUsername(token).equals(userDetails.getUsername());
  }

  public boolean isTokenExpired(String token) {
    return exactClaim(token, Claims::getExpiration).before(new Date());
  }


  private <T> T exactClaim(String token, Function<Claims, T> claimsResolver) {
    if (token == null || token.trim().isEmpty()) {
      throw new IllegalArgumentException("JWT token is missing or empty");
    }

    if (!token.contains(".") || token.chars().filter(ch -> ch == '.').count() != 2) {
      throw new IllegalArgumentException("Invalid JWT token format");
    }
    final Claims claims = Jwts.parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

    return claimsResolver.apply(claims);
  }
}
