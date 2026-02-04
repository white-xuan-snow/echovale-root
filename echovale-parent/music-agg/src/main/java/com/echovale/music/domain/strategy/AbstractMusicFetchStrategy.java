package com.echovale.music.domain.strategy;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.FetchMusicCommand;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/5 02:34
 */

@Component
public abstract class AbstractMusicFetchStrategy implements MusicFetchStrategy {

    @Override
    public List<MusicVO> fetchMusic(FetchMusicCommand command) {




        return List.of();
    }


}
