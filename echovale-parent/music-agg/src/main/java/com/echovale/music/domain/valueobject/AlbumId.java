package com.echovale.music.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:12
 */

@Value
@AllArgsConstructor
public class AlbumId {
    @JsonValue
    Long id;

    public Boolean isNull() {
        return id == null;
    }
}
