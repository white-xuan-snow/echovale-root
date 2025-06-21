package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
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
    @MppMultiId
    private Long musicId;
    @MppMultiId
    private Long tagId;
}
