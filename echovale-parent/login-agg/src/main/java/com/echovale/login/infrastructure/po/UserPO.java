package com.echovale.login.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 14:11
 */


@Value
@Builder
@AllArgsConstructor
@TableName("user")
public class UserPO {
    @TableId
    Long id;
    String username;
    String password;
    String email;
    String phone;
    Integer status;
    Long userMetaExtId;
    Long userInfoExtId;
}
