package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/11 0:09
 */
@Data
@Builder
@TableName("music_qualities")
public class MusicQualitiesPO {
    private Long id;
    private Long musicId;
    private Integer level;
    private Integer br;
    private Integer sr;
}
