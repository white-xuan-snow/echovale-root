package com.echovale.music.api.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 21:03
 */
public class FieldConstant {

    public static class MusicLevel {
        public static final String STANDARD = "standard";
        public static final String HIGHER = "higher";
        public static final String EXHIGH = "exhigh";
        public static final String LOSSLESS = "lossless";
        public static final String HIRES = "hires";
        public static final String JY_EFFECT = "jyeffect";
        public static final String JY_MASTER = "jymaster";
        public static final HashSet<String> ALL_LEVELS = new HashSet<>();
        static {
            ALL_LEVELS.add(STANDARD);
            ALL_LEVELS.add(HIGHER);
            ALL_LEVELS.add(EXHIGH);
            ALL_LEVELS.add(LOSSLESS);
            ALL_LEVELS.add(HIRES);
            ALL_LEVELS.add(JY_EFFECT);
            ALL_LEVELS.add(JY_MASTER);
        }
    }
}
