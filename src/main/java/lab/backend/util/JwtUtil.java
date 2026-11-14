package lab.backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey KEY;
    private static final long EXPIRE_MS = 24 * 60 * 60 * 1000; // 24 小时

    public JwtUtil(@Value("${app.jwt.secret}") String secret) {
        this.KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(Long userId, String username, String role) {
        return Jwts.builder()
                .subject(username)                 // 你是谁
                .claim("uid", userId)              // 附加信息
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_MS))
                .signWith(KEY)
                .compact();
    }

    public Claims parse(String token) {           // 验签；不通过会抛异常
        return Jwts.parser().verifyWith(KEY).build()
                .parseSignedClaims(token).getPayload();
    }

    public String verify(String token) {
        return parse(token).getSubject();
    }
}
