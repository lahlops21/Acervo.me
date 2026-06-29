package br.com.acervo.api.security;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  private static final long EXPIRACAO_MS = 1000L * 60 * 60 * 24; // 24h

  private SecretKey getChave() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  // Gerar Token
  public String gerarToken(String username, String role) {
    return Jwts.builder()
        .subject(username)
        .claim("role", role)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRACAO_MS))
        .signWith(getChave())
        .compact();
  }

  public boolean isTokenValido(String token, String username) {
    String usernameDoToken = extrairUsername(token);
    return usernameDoToken.equals(username) && !isExpirado(token);
  }

  private boolean isExpirado(String token) {
    return extrairClaims(token).getExpiration().before(new Date());
  }

  // Método público para que o JwtAuthFilter consiga ler os dados
  public Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(getChave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

  private String extrairUsername(String token) {
    return extrairClaims(token).getSubject();
  } 
}
