package com.echovale.music.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 15:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("lyric")
public class LyricPO {
    @TableId
    private Long musicId;
    private String neteaseLrc;
    private String neteaseTlrc;
    private String neteaseYrc;
    private String neteaseRomalrc;
    private String neteaseKlrc;
    private String amllTtml;
    private String echoTtml;
}
