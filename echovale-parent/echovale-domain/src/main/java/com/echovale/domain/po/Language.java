package com.echovale.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/11 0:10
 */
@Data
@Builder
@TableName("language")
public class Language {
    private Integer id;
    private String name;
}
