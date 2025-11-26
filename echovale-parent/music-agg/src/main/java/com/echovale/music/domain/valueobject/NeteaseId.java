package com.echovale.music.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;

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
    @JsonValue
    Long id;

    public String getNeteaseIdStr() {
        return String.valueOf(id);
    }

    public NeteaseId() {
        this.id = 0L;
    }


    public Boolean isValid() {
        return id != null && id != 0L;
    }

    public static List<Long> getNeteaseIdList(List<NeteaseId> neteaseIdList) {
        return neteaseIdList.stream()
                .map(NeteaseId::getId)
                .toList();
    }


    public static NeteaseId of(Long id) {
        if (id == null) {
            return null;
        }
        return new NeteaseId(id);
    }

}
