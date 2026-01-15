package com.echovale.login.infrastructure.security.jwt;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.shared.utils.TimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 22:39
 */

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtAuthTokenUtil {
    private String secret;
    private Long expiration;
    private String issuer;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getId());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("phone", user.getPhone());
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(TimeUtils.millis())
                .setExpiration(TimeUtils.millis(expiration * 1000000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }



    public Boolean validateToken(String token, User user) {
        Claims claims = extractAllClaims(token);
        if (isTokenExpired(claims) || isNotIssued(claims)) {
            return false;
        }
        if (!User.isNull(user)) {
            user.setId(UserId.of(claims.get("sub")));
            user.setUsername((String) claims.get(("username")));
            user.setEmail((String) claims.get("email"));
            user.setPhone((String) claims.get("phone"));
        }
        return true;
    }

    private boolean isNotIssued(Claims claims) {
        return !extractClaims(claims, Claims::getIssuer).equals(issuer);
    }

    private boolean isTokenExpired(Claims claims) {
        return extractClaims(claims, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaims(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


}
