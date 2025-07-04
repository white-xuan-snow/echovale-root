package com.echovale.service.vo;

import com.echovale.service.dto.MusicDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class PlaylistVO {
    private Long id;
    private Long neteaseId;
    private String coverUrl;
    private String name;
    private String description;
    private String updateTime;
    private String createTime;
    private List<String> tags;
    private List<MusicDTO> musics;
}
