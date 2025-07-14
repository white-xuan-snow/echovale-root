package com.echovale.domain.po;

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
 * @date 2025/6/11 0:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tag")
public class TagPO {
    @TableId
    private Long id;
    private String name;
}
