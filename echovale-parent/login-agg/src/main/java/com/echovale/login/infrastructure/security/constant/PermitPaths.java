package com.echovale.login.infrastructure.security.constant;

import com.echovale.login.infrastructure.constant.Auth;
import com.echovale.shared.domain.constant.GlobalPaths;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 16:51
 */
public class PermitPaths {

    public final static String[] PERMIT_PATHS = {
            Auth.LOGIN,
            Auth.REGISTER,
            GlobalPaths.PUBLIC,
            // TODO 已开启全局路径
            GlobalPaths.ALL
    };




}
