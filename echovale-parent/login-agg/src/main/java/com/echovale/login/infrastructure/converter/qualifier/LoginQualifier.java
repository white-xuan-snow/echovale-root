package com.echovale.login.infrastructure.converter.qualifier;

import com.echovale.login.domain.entity.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/31 20:54
 */

@Component
@RequiredArgsConstructor
public class LoginQualifier {
    public LoginType mapRefreshLoginType() {
        return LoginType.REFRESH;
    }
}
