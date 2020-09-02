package yteproject.application.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtil {

    public static String generateToken(Authentication authentication, String secretKey, Integer expirationDays) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities",getAuthorities(authentication))
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate(expirationDays))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    private static List<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
    }

    private static Date calculateExpirationDate(Integer expirationDays) {
        Instant expirationTime = LocalDate.now()
                .plusDays(expirationDays)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(expirationTime);
    }

    public static String extractUsername(String jwtToken, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
