package com.echovale.music.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:50
 */

@Value
@AllArgsConstructor
public class MusicInfoExtend {
    LocalDateTime publishTime;
    Integer no;
    Integer bpm;
}
