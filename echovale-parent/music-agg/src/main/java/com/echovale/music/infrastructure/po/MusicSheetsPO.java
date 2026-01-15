package com.echovale.music.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * @date 2025/6/11 0:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("music_sheets")
public class MusicSheetsPO {
    @MppMultiId
    private Long id;
    @MppMultiId
    private Long musicId;
    private String name;
    private String url;
}
