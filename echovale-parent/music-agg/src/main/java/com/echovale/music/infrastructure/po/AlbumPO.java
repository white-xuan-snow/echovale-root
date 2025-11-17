package com.echovale.music.infrastructure.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2025/6/10 15:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("album")
public class AlbumPO {
    @TableId
    private Long id;
    private String name;
    private Long neteaseId;
    private String description;
    private String picUrl;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime publishTime;
    private String type;
    private Integer size;
    private String subType;
}
