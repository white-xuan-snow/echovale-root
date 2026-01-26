package com.echovale.login.infrastructure.security.advice;

import com.alibaba.fastjson.JSONObject;
import com.echovale.shared.infrastructure.presistence.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.echovale.shared.domain.valueobject.Strings;

import java.io.IOException;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 23:46
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Strings.UTF_8);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());


        // TODO AuthenticationException异常包装过渡，丢失根本异常信息
        String msg = "认证失败：";
        Throwable cause = authException.getCause();
        if (cause instanceof ExpiredJwtException) {
            msg += "Token已过期";
        } else if (cause instanceof MalformedJwtException) {
            msg += "Token格式错误";
        } else {
            msg += authException.getMessage();
        }

        Result failResult = Result.fail(HttpStatus.UNAUTHORIZED.value(), msg);
        response.getWriter().write(JSONObject.toJSONString(failResult));
    }
}
