package com.echovale.playlist.domain.aggregate;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.domain.valueobject.PlaylistId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/12 15:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private PlaylistId id;
    private NeteaseId neteaseId;
    private String coverUrl;
    private String name;
    private String description;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
    private List<String> tags;
    private List<MusicId> musicIds;
}
