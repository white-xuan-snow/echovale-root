package com.echovale.login.api.dto;

import com.echovale.login.domain.entity.ClientType;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/31 20:41
 */

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest {
    ClientType clientType;
    String deviceId;
}
