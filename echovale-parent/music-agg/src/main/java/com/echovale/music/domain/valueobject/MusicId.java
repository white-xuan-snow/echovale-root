package com.echovale.music.domain.valueobject;

import com.echovale.music.infrastructure.po.MusicPO;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:18
 */

@Value
@Getter
@AllArgsConstructor
public class MusicId {
    @JsonValue
    Long id;


    public MusicId() {
        this.id = 0L;
    }

    public Boolean isValid() {
        return id != null && id != 0L;
    }


    public static List<MusicId> getEmptyList(int size) {
        List<MusicId> emptyList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            emptyList.add(new MusicId(0L));
        }
        return emptyList;
    }




    public static MusicId of(Long id) {
        if (id == null) {
            return new MusicId();
        }
        return new MusicId(id);
    }


    public static MusicId of(MusicPO musicPO) {
        if (musicPO == null) {
            return MusicId.of(0L);
        }
        return new MusicId(musicPO.getId());
    }
}
