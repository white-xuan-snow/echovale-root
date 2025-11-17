package com.echovale.music.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/11 0:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("music_authors")
public class MusicAuthorsPO {
    @MppMultiId
    private Long authorId;
    @MppMultiId
    private Long musicId;
}
