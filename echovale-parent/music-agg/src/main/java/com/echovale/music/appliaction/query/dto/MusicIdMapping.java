package com.echovale.music.appliaction.query.dto;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 20:02
 */

@Value
@AllArgsConstructor
public class MusicIdMapping {
    MusicId id;
    NeteaseId neteaseId;
}
