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
 * @date 2025/7/16 19:46
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchVO {
    /**
     * @description: 歌曲数量
     */
    private Integer songCount;
    /**
     * @description: 网易云音乐List
     */
    private List<MusicDTO> neteaseMusics;
}
