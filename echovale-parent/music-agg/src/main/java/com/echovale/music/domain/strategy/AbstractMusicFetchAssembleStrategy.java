package com.echovale.music.domain.strategy;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.BaseFetchMusicCommand;
import com.echovale.music.appliaction.service.MusicApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/5 02:34
 */

@Component
public abstract class AbstractMusicFetchAssembleStrategy<T> implements MusicFetchAssembleStrategy {

    @Autowired
    MusicApplicationService musicApplicationService;

    @Override
    public T definedAssemblyFlow(BaseFetchMusicCommand command) {
        // 抽象预处理，获取MusicIds或NeteaseIds
        MusicFetchKeywords musicFetchKeywords = obtainKeywords(command);

        // 核心加载
        List<MusicVO> musicVOList = fetchMusicList(musicFetchKeywords);

        // 抽象处理
        return finalResolve(musicVOList);
    }

    protected abstract T finalResolve(List<MusicVO> musicVOList);

    private List<MusicVO> fetchMusicList(MusicFetchKeywords musicFetchKeywords) {
        return musicApplicationService.loadMusicList(musicFetchKeywords);
    }

    protected abstract MusicFetchKeywords obtainKeywords(BaseFetchMusicCommand command);


}
