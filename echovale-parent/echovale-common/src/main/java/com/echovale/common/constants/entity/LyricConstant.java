package com.echovale.common.constants.entity;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/15 17:21
 */
public class LyricConstant {

    private LyricConstant() {} // 防止实例化

    public static final String NETEASE_LRC = "neteaseLrc";
    public static final String NETEASE_TLRC = "neteaseTlrc";
    public static final String NETEASE_YRC = "neteaseYrc";
    public static final String NETEASE_ROMALRC = "neteaseRomalrc";
    public static final String NETEASE_KLRC = "neteaseKlrc";
    public static final String AMLL_TTML = "amllTtml";
    public static final String ECHO_TTML = "echoTtml";

    public static final HashSet<String> ALL_FIELDS = new HashSet<>(
            Arrays.asList(NETEASE_LRC, NETEASE_TLRC, NETEASE_YRC,
                    NETEASE_ROMALRC, NETEASE_KLRC, AMLL_TTML, ECHO_TTML)
    );




}
