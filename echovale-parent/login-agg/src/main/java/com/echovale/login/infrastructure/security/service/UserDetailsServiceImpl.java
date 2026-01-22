package com.echovale.login.infrastructure.security.service;

import com.echovale.login.application.command.ObtainUserRolesCommand;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.application.service.RolesApplicationService;
import com.echovale.login.application.service.UserApplicationService;
import com.echovale.login.infrastructure.security.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 23:23
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserApplicationService userApplicationService;
    private final RolesApplicationService rolesApplicationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO UserApplicationService需要Command形式传参
        User user = userApplicationService.obtainUser(username);

        ObtainUserRolesCommand obtainUserRolesCommand = ObtainUserRolesCommand.of(user);

        List<String> roles =  rolesApplicationService.obtainRoles(obtainUserRolesCommand);

        return new UserDetailsImpl(user, roles);
    }
}
