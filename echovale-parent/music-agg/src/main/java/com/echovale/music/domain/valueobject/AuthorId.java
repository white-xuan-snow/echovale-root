package com.echovale.music.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:15
 */


@Value
@AllArgsConstructor
public class AuthorId {
    @JsonValue
    Long id;


    public AuthorId() {
        this.id = 0L;
    }

    public static List<AuthorId> byLongIds(List<Long> authorLongIds) {
        return authorLongIds.stream()
                .map(AuthorId::of)
                .toList();
    }


    public Boolean isValid() {
        return id != null && id != 0L;
    }

    public static List<Long> getIds(List<AuthorId> authorIds) {
        return authorIds.stream()
                .map(AuthorId::getId)
                .toList();
    }


    public static AuthorId of(Long id) {
        if (id == null) {
            return new AuthorId();
        }
        return new AuthorId(id);
    }
}
