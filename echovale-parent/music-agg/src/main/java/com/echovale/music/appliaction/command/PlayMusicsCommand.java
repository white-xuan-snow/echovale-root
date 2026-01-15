package com.echovale.music.appliaction.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/23 08:35
 */


@Value
@Builder
@AllArgsConstructor
public class PlayMusicsCommand {
    List<Long> ids;
    List<Long> neteaseIds;

    public Boolean withoutIds() {
        return ids == null || ids.isEmpty();
    }






}
