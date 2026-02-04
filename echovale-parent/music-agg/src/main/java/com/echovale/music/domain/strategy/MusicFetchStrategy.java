package com.echovale.music.domain.strategy;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.FetchMusicCommand;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/5 02:29
 */

public interface MusicFetchStrategy {

    MusicFetchSource getFetchSource();

    List<MusicVO> fetchMusic(FetchMusicCommand command);

}
