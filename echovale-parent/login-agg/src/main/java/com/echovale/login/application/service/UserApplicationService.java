package com.echovale.login.application.service;


import com.echovale.login.domain.aggregate.User;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 23:32
 */

@Service
public interface UserApplicationService {
    User obtainUser(String username);
}
