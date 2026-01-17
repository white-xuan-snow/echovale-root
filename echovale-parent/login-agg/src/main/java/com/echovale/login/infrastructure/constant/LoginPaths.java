package com.echovale.login.infrastructure.constant;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 16:39
 */


public class LoginPaths {


    private static final String V1 = "/v1";

    public static final String LOGIN = V1 + "/auth/login";

    public static final String LOGOUT = V1 + "/auth/logout";

    public static final String REGISTER = V1 + "/auth/register";


    public static class Test {
        public static final String TEST = "/test" + V1 + "/auth";
        public static final String REFRESH_TOKEN = "/refresh";
    }
}
