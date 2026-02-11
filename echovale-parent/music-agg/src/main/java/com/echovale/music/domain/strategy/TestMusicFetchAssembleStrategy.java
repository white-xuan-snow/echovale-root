package com.echovale.music.domain.strategy;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.BaseFetchMusicCommand;
import com.echovale.music.appliaction.command.FetchMusicCommand;
import com.echovale.music.infrastructure.converter.MusicFetchKeywordsConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/11 22:33
 */

@Component
@RequiredArgsConstructor
public class TestMusicFetchAssembleStrategy extends AbstractMusicFetchAssembleStrategy<List<MusicVO>> {

    private final MusicFetchKeywordsConverter musicFetchKeywordsConverter;

    @Override
    protected List<MusicVO> finalResolve(List<MusicVO> musicVOList) {
        return musicVOList;
    }

    @Override
    protected MusicFetchKeywords obtainKeywords(BaseFetchMusicCommand command) {
        if (command instanceof FetchMusicCommand fetchMusicCommand) {
            return musicFetchKeywordsConverter.byFetchMusicCommand(fetchMusicCommand);
        } else {
            throw new RuntimeException("传入参数错误");
        }
    }

    @Override
    public MusicFetchSource getFetchSource() {
        return MusicFetchSource.TEST;
    }
}
