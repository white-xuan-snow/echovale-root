package com.echovale.login.application.service.impl;

import com.echovale.login.application.command.ObtainUserRolesCommand;
import com.echovale.login.application.service.RolesApplicationService;
import com.echovale.login.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 23:37
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class RolesApplicationServiceImpl implements RolesApplicationService {


    @Override
    public List<String> obtainRoles(ObtainUserRolesCommand obtainUserRolesCommand) {
        UserId userId = obtainUserRolesCommand.getUserId();
        return List.of("user");
    }
}
