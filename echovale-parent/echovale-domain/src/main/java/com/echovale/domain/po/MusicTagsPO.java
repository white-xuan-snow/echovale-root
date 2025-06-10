package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/11 0:19
 */
@Data
@Builder
@TableName("music_tags")
public class MusicTagsPO {
    private Long musicId;
    private Long tagId;
}
