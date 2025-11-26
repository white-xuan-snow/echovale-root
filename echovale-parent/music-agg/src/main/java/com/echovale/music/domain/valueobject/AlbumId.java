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

    public AlbumId() {
        this.id = 0L;
    }


    public Boolean isValid() {
        return id != null && id != 0L;
    }


    public static AlbumId of(Long id) {
        if (id == null) {
            return new AlbumId();
        }
        return new AlbumId(id);
    }
}
