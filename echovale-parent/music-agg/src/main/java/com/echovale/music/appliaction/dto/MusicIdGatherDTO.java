package com.echovale.music.appliaction.dto;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/24 15:03
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicIdGatherDTO {
    public static MusicIdGatherDTO EMPTY = new MusicIdGatherDTO();
    List<MusicId> existentMusicIdList;
    List<NeteaseId> nonexistentMusicNeteaseIdList;
}
