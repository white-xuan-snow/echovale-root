package com.echovale.music.domain.entity;

import com.echovale.music.domain.valueobject.MusicId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:37
 */

@Getter
@Builder
@AllArgsConstructor
public class MusicQuality {
    private final Long id;
    private final MusicId musicId;
    private final String level;
    private final Integer br;
    private final Integer sr;
    private final Integer size;



    public static MusicQuality createNew(Long musicId, String level, Integer br, Integer sr, Integer size){
        return MusicQuality.builder()
                .musicId(MusicId.of(musicId))
                .level(level)
                .br(br)
                .sr(sr)
                .size(size)
                .build();
    }



    public enum QualityLevel {
        STANDARD(1, "standard"),
        HIGHER(2, "higher"),
        EX_HIGH(3, "exhigh"),
        LOSSLESS(4, "lossless"),
        HIRES(5, "hires"),
        JY_EFFECT(6, "jyeffect"),
        JY_MASTER(7, "jymaster"),
        ;


        @Getter
        private final int code;
        @Getter
        private final String desc;

        QualityLevel(int i, String desc) {
            this.code = i;
            this.desc = desc;
        }

        public static QualityLevel of(int code) {
            for (QualityLevel qualityLevel : values()) {
                if (qualityLevel.code == code) {
                    return qualityLevel;
                }
            }
            throw new IllegalArgumentException("unknown quality level code: " + code);
        }


        public static QualityLevel of(String desc) {
            for (QualityLevel qualityLevel : values()) {
                if (qualityLevel.desc.equals(desc)) {
                    return qualityLevel;
                }
            }
            throw new IllegalArgumentException("unknown quality level desc: " + desc);
        }


    }
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

