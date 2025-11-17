package com.echovale.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/8/15 1:45
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NeteaseCookieDTO {
    @NotNull
    String cookie;
}
