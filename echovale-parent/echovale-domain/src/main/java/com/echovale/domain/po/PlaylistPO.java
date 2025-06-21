package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/17 19:03
 */
@Data
@Builder
@TableName("playlist")
public class PlaylistPO {
    @TableId
    private Long id;
    private Long neteaseId;
    private String coverUrl;
    private String name;
    private String description;
    private Long updateTime;
    private Long createTime;
    private String tags;
    private Boolean isUser;
}
