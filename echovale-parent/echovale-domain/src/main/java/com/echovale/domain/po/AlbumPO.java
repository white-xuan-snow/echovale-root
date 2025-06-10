package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 15:26
 */
@Data
@Builder
@TableName("album")
public class AlbumPO {
    private Long id;
    private String name;
    private Long neteaseId;
    private String description;
    private String picUrl;
    private String publishTime;
    private Integer type;
    private Integer size;
    private Integer subType;
}
