package com.echovale.music.domain.valueobject;

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
    Long id;

    public static List<MusicId> getEmptyList(int size) {
        List<MusicId> emptyList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            emptyList.add(new MusicId(0L));
        }
        return emptyList;
    }
}
