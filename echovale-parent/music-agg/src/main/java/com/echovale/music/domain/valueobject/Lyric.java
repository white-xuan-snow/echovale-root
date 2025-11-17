package com.echovale.music.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:26
 */

@Value
@Builder
@AllArgsConstructor
public class Lyric {
    String neteaseLrc;
    String neteaseTlrc;
    String neteaseYrc;
    String neteaseRomalrc;
    String neteaseKlrc;
    String amllTtml;
    String echoTtml;
}
