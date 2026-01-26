package com.echovale.playlist.api.vo;

import com.echovale.shared.infrastructure.presistence.PageResult;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.domain.valueobject.PlaylistId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/2 08:27
 */


@Value
@Builder
@AllArgsConstructor
public class PlaylistVO {
    PlaylistId id;
    NeteaseId neteaseId;
    String coverUrl;
    String name;
    String description;
    LocalDateTime updateTime;
    LocalDateTime createTime;
    List<String> tags;
    PageResult<MusicVO> musics;
}
