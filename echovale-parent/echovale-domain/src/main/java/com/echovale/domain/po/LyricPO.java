package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 15:30
 */
@Data
@Builder
@TableName("lyric")
public class LyricPO {
    private Long musicId;
    private String neteaseLrc;
    private String neteaseTlrc;
    private String neteaseYrc;
    private String neteaseRomalyc;
    private String neteaseKlrc;
    private String amllTtml;
    private String echoTtml;
}
