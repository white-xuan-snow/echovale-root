package com.echovale.login.domain.service;


import com.echovale.login.application.command.ObtainUserRolesCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolesApplicationService {
    List<String> obtainRoles(ObtainUserRolesCommand obtainUserRolesCommand);
}
