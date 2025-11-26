package com.echovale.music.domain.entity;

import com.echovale.music.domain.valueobject.MusicId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
}

