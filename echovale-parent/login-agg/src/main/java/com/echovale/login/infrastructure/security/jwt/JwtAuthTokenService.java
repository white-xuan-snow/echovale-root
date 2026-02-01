package com.echovale.login.infrastructure.security.jwt;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.echovale.login.application.command.LoginCommand;
import com.echovale.login.domain.service.TokenStoreService;
import com.echovale.login.infrastructure.properties.JwtAuthProperties;
import com.echovale.login.infrastructure.properties.LoginRedisProperties;
import com.echovale.login.infrastructure.security.exception.AccessTokenExpiredException;
import com.echovale.login.infrastructure.security.exception.AccessTokenInvalidException;
import com.echovale.login.infrastructure.security.exception.RefreshTokenExpiredException;
import com.echovale.login.infrastructure.security.exception.RefreshTokenInvalidException;
import com.echovale.shared.domain.exception.UnauthorizedException;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.shared.infrastructure.utils.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 30531
 */
@Service
@Data
@RequiredArgsConstructor
public class JwtAuthTokenService {

    private final TokenStoreService tokenStoreService;


    private SecretKey getSigningKey() {
        byte[] keyBytes = JwtAuthProperties.SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        claims.put("jti", generateJti());
        // 注意：Subject 习惯上存放唯一的 UserId
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("phone", user.getPhone());
        // TODO 权限信息
        return createToken(claims, user.getId().getStringValue(), JwtAuthProperties.ACCESS_EXPIRATION);
    }

    public String generateRefreshToken(User user, LoginCommand command) {
        long remainingTtl = tokenStoreService.getRemainingTtl(user.getId(), command.getClientType(), command.getDeviceId());
        long threshold = LoginRedisProperties.TOKEN_REFRESH_THRESHOLD.toSeconds();

        // 如果剩余 TTL 大于阈值，则直接返回旧的 RefreshToken
        if (remainingTtl > threshold) {
            return command.getOldRefreshToken();
        }

        Map<String, Object> claims = new HashMap<>();
        String jti = generateJti();
        claims.put("type", "refresh");
        claims.put("jti", jti);
        String refreshToken = createToken(claims, user.getId().getStringValue(), JwtAuthProperties.REFRESH_EXPIRATION);
        tokenStoreService.recordRefresh(user.getId(), command.getClientType(), command.getDeviceId(), jti);
        return refreshToken;
    }

    private String createToken(Map<String, Object> claims, String subject, Duration expiration) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(JwtAuthProperties.ISSUER)
                .setIssuedAt(new Date(now))
                // 假设配置单位是秒
                .setExpiration(new Date(now + expiration.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateAccessToken(String token, User user) {
        try {
            Claims claims = extractAllClaims(token);

            if (isTokenExpired(claims)) {
                throw new AccessTokenExpiredException();
            }
            if (isNotIssued(claims)) {
                throw new AccessTokenInvalidException();
            }

            String type = claims.get("type", String.class);
            if (!"access".equals(type)) {
                throw new AccessTokenInvalidException();
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
            throw new AccessTokenInvalidException();
        }
    }

    public Boolean validateRefreshToken(LoginCommand command, User user) {
        try {
            Claims claims = extractAllClaims(command.getOldRefreshToken());

            if (isTokenExpired(claims)) {
                throw new RefreshTokenExpiredException();
            }
            if (isNotIssued(claims)) {
                throw new RefreshTokenInvalidException();
            }

            String type = claims.get("type", String.class);
            if (!"refresh".equals(type)) {
                throw new RefreshTokenInvalidException();
            }

            if (user != null) {
                // 从 sub (Subject) 中恢复 UserId
                user.setId(UserId.of(claims.getSubject()));

                String jti = claims.get("jti", String.class);

                return tokenStoreService.isRefreshValid(user.getId(), command.getClientType(), command.getDeviceId(), jti);
            }
            return true;
        } catch (Exception e) {
            // TODO 异常处理与抛出异常
            // 解析失败（签名错误、格式错误等）直接判定无效
            throw new RefreshTokenInvalidException();
        }
    }

    private boolean isNotIssued(Claims claims) {
        return !JwtAuthProperties.ISSUER.equals(claims.getIssuer());
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


    private String generateJti() {
        return NanoIdUtils.randomNanoId();
    }
}