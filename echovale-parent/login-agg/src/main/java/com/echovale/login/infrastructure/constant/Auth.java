package com.echovale.login.infrastructure.constant;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 16:39
 */


public class Auth {


    private static final String V1 = "/v1";

    public static final String PREFIX = V1 + "/auth";

    public static final String LOGIN_SUFFIX = "/login";
    public static final String LOGIN = PREFIX + LOGIN_SUFFIX;

    public static final String LOGOUT_SUFFIX = "/logout";
    public static final String LOGOUT = PREFIX + LOGOUT_SUFFIX;

    public static final String REGISTER = "/register";
    public static final String REGISTER_SUFFIX = "/register";
    public static final String REFRESH_SUFFIX = "/refresh";
    public static final String REFRESH = PREFIX + REFRESH_SUFFIX;


    public static class Test {
        public static final String TEST = "/test" + V1 + "/auth";
        public static final String REFRESH_TOKEN = "/refresh";
    }
}
