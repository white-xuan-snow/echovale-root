package com.echovale.login.domain.entity;

import com.echovale.login.domain.valueobject.UserInfoExtId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 22:45
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoExt {
    UserInfoExtId id;
}
