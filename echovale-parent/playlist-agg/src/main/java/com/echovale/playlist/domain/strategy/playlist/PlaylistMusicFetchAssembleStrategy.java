package com.echovale.playlist.domain.strategy.playlist;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.BaseFetchMusicCommand;
import com.echovale.music.domain.strategy.AbstractMusicFetchAssembleStrategy;
import com.echovale.music.domain.strategy.MusicFetchKeywords;
import com.echovale.music.domain.strategy.MusicFetchSource;
import com.echovale.playlist.api.vo.PlaylistVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/11 22:16
 */

@Component
@RequiredArgsConstructor
public class PlaylistMusicFetchAssembleStrategy extends AbstractMusicFetchAssembleStrategy<PlaylistVO> {
    @Override
    protected PlaylistVO finalResolve(List<MusicVO> musicVOList) {
        return null;
    }

    @Override
    protected MusicFetchKeywords obtainKeywords(BaseFetchMusicCommand command) {
        return null;
    }

    @Override
    public MusicFetchSource getFetchSource() {
        return MusicFetchSource.PLAYLIST;
    }
}
