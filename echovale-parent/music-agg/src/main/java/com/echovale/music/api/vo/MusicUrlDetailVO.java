package com.echovale.music.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 23:07
 */
@Value
@Setter
@Builder
@AllArgsConstructor
public class MusicUrlDetailVO {
    Long id;
    Long neteaseId;
    String url;
    Integer size;
    Integer time;
    String level;
    Integer fee;
    Integer br;
    Integer sr;
    String type;
    String flag;
}
