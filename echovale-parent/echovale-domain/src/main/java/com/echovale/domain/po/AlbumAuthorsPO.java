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
 * @date 2025/6/11 0:16
 */
@Data
@Builder
@TableName("album_authors")
public class AlbumAuthorsPO {
    @MppMultiId
    private Long albumId;
    @MppMultiId
    private Long authorId;
}
