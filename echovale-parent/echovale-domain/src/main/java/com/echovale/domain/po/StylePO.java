package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/11 0:05
 */
@Data
@Builder
@TableName("style")
public class StylePO {
    private Long id;
    private String name;
}
