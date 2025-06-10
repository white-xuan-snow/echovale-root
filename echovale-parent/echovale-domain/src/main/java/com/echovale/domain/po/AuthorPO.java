package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 15:28
 */
@Data
@Builder
@TableName("author")
public class AuthorPO {
    private Long id;
    private String name;
    private Long neteaseId;
    private String transName;
    private String alias;
    private Integer musicSize;
    private Integer albumSize;
    private Integer mvSize;
    private String coverUrl;
    private String avatarUrl;
    private String description;
    private String identify;
}
