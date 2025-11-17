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
@TableName("album_authors")
public class AlbumAuthorsPO {
    @MppMultiId
    private Long albumId;
    @MppMultiId
    private Long authorId;
}
