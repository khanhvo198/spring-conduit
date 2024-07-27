package mystic.conduit.utils;

import ch.qos.logback.core.net.SyslogOutputStream;
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
        System.out.println(token);
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
            String subject = claims.getSubject();
            System.out.println(subject);
            return subject;

        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String encode (String email) {
        try {
            return Jwts.builder().subject(email).signWith(key).compact();
        } catch (JwtException e) {
            return null;
        }
    }



}
