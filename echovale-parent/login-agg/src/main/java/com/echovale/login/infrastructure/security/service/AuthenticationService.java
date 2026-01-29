package com.echovale.login.infrastructure.security.service;

import com.echovale.login.domain.aggregate.User;
import com.echovale.login.infrastructure.security.entity.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/29 21:58
 */

@Service
public class AuthenticationService {
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUser();
        }
        return null;
    }
}
