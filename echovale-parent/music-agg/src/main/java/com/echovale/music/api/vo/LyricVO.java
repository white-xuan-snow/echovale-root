package com.echovale.music.api.vo;

import lombok.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/28 14:18
 */


@Value
@Builder
@AllArgsConstructor
public class LyricVO {
    String neteaseLrc;
    String neteaseTlrc;
    String neteaseYrc;
    String neteaseRomalrc;
    String neteaseKlrc;
    String amllTtml;
    String echoTtml;
}
