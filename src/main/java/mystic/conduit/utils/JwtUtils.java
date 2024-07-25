package mystic.conduit.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JwtUtils {
    private final Key key;

    public JwtUtils(String secretKey, Long expiration) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    public boolean validateToken (String token) {
        try {
            Date exp = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload().getExpiration();
            return exp.after(Date.from(Instant.now()));
        } catch (JwtException e) {
            return false;
        }
    }

    public String getSubject (String token) {
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
            return claims.getSubject();

        } catch (JwtException e) {
            return null;
        }
    }


}
