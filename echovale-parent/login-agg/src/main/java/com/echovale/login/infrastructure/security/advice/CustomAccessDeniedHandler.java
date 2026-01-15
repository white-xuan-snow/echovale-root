package com.echovale.login.infrastructure.security.advice;

import com.alibaba.fastjson.JSONObject;
import com.echovale.common.domain.infrastructure.presistence.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 23:53
 */

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Result failResult = Result.fail(HttpStatus.FORBIDDEN.value(), "权限不足");
        response.getWriter().write(JSONObject.toJSONString(failResult));
    }
}
