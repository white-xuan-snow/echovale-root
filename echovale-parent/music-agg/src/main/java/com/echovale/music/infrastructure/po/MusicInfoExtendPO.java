package com.echovale.music.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 21:44
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("music_info_ext")
public class MusicInfoExtendPO {
    @TableId
    private Long musicId;
    private LocalDateTime publishTime;
    private Integer no;
    private Integer bpm;
}
