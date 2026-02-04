package com.echovale.music.appliaction.command;

import com.echovale.music.domain.strategy.MusicFetchSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/5 02:32
 */

@Value
@Builder
@AllArgsConstructor
public class FetchMusicCommand {
    MusicFetchSource musicFetchSource;
    Object identifier;
    int limit;
    int offset;
}
