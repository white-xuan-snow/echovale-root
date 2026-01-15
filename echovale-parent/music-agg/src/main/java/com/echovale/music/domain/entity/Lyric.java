package com.echovale.music.domain.entity;

import com.echovale.music.domain.valueobject.MusicId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.HashSet;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:26
 */

@Data
@Builder
@AllArgsConstructor
public class Lyric {
    MusicId musicId;
    String neteaseLrc;
    String neteaseTlrc;
    String neteaseYrc;
    String neteaseRomalrc;
    String neteaseKlrc;
    String amllTtml;
    String echoTtml;

    public static class LyricType{
        public static final String NETEASE_LRC = "neteaseLrc";
        public static final String NETEASE_TLRC = "netaseTlrc";
        public static final String NETEASE_YRC = "neteaseYrc";
        public static final String NETEASE_ROMALRC = "neteaseRomanlrc";
        public static final String NETEASE_KLRC = "neteaseKlrc";
        public static final String AMLL_TTML = "amllTtml";
        public static final String ECHO_TTML = "echoTtml";

        public static final HashSet<String> ALL_LYRIC_TYPES = new HashSet<>();

        static {
            ALL_LYRIC_TYPES.add(NETEASE_LRC);
            ALL_LYRIC_TYPES.add(NETEASE_TLRC);
            ALL_LYRIC_TYPES.add(NETEASE_YRC);
            ALL_LYRIC_TYPES.add(NETEASE_ROMALRC);
            ALL_LYRIC_TYPES.add(NETEASE_KLRC);
            ALL_LYRIC_TYPES.add(AMLL_TTML);
            ALL_LYRIC_TYPES.add(ECHO_TTML);
        }
    }
}
