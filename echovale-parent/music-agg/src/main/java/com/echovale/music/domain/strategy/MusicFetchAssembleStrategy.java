package com.echovale.music.domain.strategy;

import com.echovale.music.appliaction.command.BaseFetchMusicCommand;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/5 02:29
 */

public interface MusicFetchAssembleStrategy<T> {

    MusicFetchSource getFetchSource();

    T definedAssemblyFlow(BaseFetchMusicCommand command);

}
