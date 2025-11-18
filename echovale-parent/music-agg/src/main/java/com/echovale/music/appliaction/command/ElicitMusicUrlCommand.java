package com.echovale.music.appliaction.command;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 16:41
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElicitMusicUrlCommand {
    List<Long> ids;
    List<Long> neteaseIds;

    String level;


    public boolean withoutNeteaseIds() {
        return neteaseIds == null || neteaseIds.isEmpty();
    }

}
