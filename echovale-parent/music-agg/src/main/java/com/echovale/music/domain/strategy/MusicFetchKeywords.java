package com.echovale.music.domain.strategy;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/11 21:32
 */

@Value
@Builder
@AllArgsConstructor
public class MusicFetchKeywords {
    List<MusicId> musicIds;
    List<NeteaseId> neteaseIds;
}
