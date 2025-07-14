package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/17 19:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("playlist")
public class PlaylistPO {
    @TableId
    private Long id;
    private Long neteaseId;
    private String coverUrl;
    private String name;
    private String description;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private String tags;
    private Boolean isUser;
}
