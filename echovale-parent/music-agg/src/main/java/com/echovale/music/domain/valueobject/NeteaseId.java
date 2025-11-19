package com.echovale.music.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 23:12
 */
@Getter
@Value
@AllArgsConstructor
public class NeteaseId {
    Long id;

    public String getNeteaseIdStr() {
        return String.valueOf(id);
    }

}
