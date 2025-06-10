package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 21:44
 */

@Data
@Builder
@TableName("music_info_ext")
public class MusicInfoExtendPO {
    private Long musicId;
    private String publishTime;
    private Integer no;
    private Integer bpm;
}
