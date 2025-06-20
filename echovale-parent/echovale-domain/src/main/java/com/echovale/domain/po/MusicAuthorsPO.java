package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("music_authors")
public class MusicAuthorsPO {
    private Long authorId;
    private Long musicId;
}
