package com.echovale.music.appliaction.command;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/11 22:37
 */

@Value
@SuperBuilder
@AllArgsConstructor
public class FetchMusicCommand extends BaseFetchMusicCommand{
    List<Long> musicIds;
    List<Long> neteaseIds;
}
