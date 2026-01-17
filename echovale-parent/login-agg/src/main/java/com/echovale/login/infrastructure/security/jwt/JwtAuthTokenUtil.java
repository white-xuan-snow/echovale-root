package com.echovale.login.infrastructure.security.jwt;

import com.echovale.common.domain.api.exception.UnauthorizedException;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.valueobject.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "login.security.jwt")
@Data
public class JwtAuthTokenUtil {
    private String secret;
    private Duration accessExpiration;
    private Duration refreshExpiration;
    private String issuer;

    /**
     * 生成安全密钥：JJWT 0.11+ 推荐方式
     * 解决 javax.xml.bind.DatatypeConverter 缺失问题
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        // 注意：Subject 习惯上存放唯一的 UserId
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("phone", user.getPhone());
        // TODO 权限信息
        return createToken(claims, user.getId().getStringValue(), accessExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, Duration expiration) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date(now))
                // 假设配置单位是秒
                .setExpiration(new Date(now + expiration.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token, User user) {
        try {
            Claims claims = extractAllClaims(token);

            if (isTokenExpired(claims) || isNotIssued(claims)) {
                return false;
            }

            if (user != null) {
                // 从 sub (Subject) 中恢复 UserId
                user.setId(UserId.of(claims.getSubject()));
                user.setUsername(claims.get("username", String.class));
                user.setEmail(claims.get("email", String.class));
                user.setPhone(claims.get("phone", String.class));
            }
            return true;
        } catch (Exception e) {
            // TODO 异常处理与抛出异常
            // 解析失败（签名错误、格式错误等）直接判定无效
            throw new UnauthorizedException("Token错误");
        }
    }

    private boolean isNotIssued(Claims claims) {
        return !issuer.equals(claims.getIssuer());
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        // JJWT 0.11+ 使用 parserBuilder()
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}