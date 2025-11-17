package com.echovale.music.appliaction.command;

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
public class ElicitMusicUrlCommand {
    List<Long> ids;
    List<Long> neteaseIds;

    String level;


    public boolean hasNeteaseIds() {
        return neteaseIds != null && !neteaseIds.isEmpty();
    }
}
