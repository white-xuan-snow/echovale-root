package com.echovale.login.domain.service.impl;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.service.UserApplicationService;
import com.echovale.login.domain.valueobject.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 23:33
 */

@Slf4j
@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    @Override
    public User obtainUser(String username) {
        return User.builder()
                .id(new UserId())
                .username(username)
                .email("123@123.com")
                .phone("12312312311")
                .build();
    }
}
