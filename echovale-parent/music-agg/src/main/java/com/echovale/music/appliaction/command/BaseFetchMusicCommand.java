package com.echovale.music.appliaction.command;

import com.echovale.music.domain.strategy.MusicFetchSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/5 02:32
 */

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseFetchMusicCommand {
    MusicFetchSource musicFetchSource;
    int limit;
    int offset;
}
