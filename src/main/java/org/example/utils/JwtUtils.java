package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.service.dto.UserType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JwtUtils {

    private static final String SECRET_KEY = "JMPCD9FopWAh1JpNaqnuzog06Y8J6VNykMWOh7Gt00vs18Jk7XfEd9gjjPIR7mj";

    private JwtUtils() {
    }

    public static String generateToken(String username, UserType userType) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setSubject(userType.name())
                .compact();
    }

    public static String refreshToken(String token) {
        try {
            final Claims claims = getClaims(token);
// Sprawdzenie czy token jest nadal aktywny
            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();
            if (expirationDate.after(currentDate)) {
// Token jest aktywny, można wygenerować nowy token
                final String username = claims.get("username", String.class);
                final UserType userType = UserType.valueOf(claims.getSubject());
                return generateToken(username, userType);
            } else {
// Token wygasł, nie można odświeżyć
                return null;
            }
        } catch (Exception e) {
            return null; // Token jest niepoprawny
        }
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.replace("Bearer ",""))
                .getBody()
                .get("username", String.class);
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(getTokenWithoutBearer(token))
                .getBody();
    }

    private static String getTokenWithoutBearer(String token) {
        return token.replace("Bearer ", "");
    }
}