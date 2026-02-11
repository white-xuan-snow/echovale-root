package com.echovale.music.domain.strategy;

import com.echovale.music.appliaction.command.BaseFetchMusicCommand;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.shared.domain.exception.NotImplementedException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/2/11 23:18
 */


@Component
@RequiredArgsConstructor
public class MusicFetchContext {
    private final List<MusicFetchAssembleStrategy<?>> musicFetchStrategies;
    private Map<MusicFetchSource, MusicFetchAssembleStrategy<?>> musicFetchStrategyMap;

    @PostConstruct
    public void init() {
        musicFetchStrategyMap = musicFetchStrategies.stream()
                .collect(Collectors.toMap(MusicFetchAssembleStrategy::getFetchSource, strategy -> strategy));
    }


    public Object execute(BaseFetchMusicCommand command) {
        MusicFetchAssembleStrategy<?> strategy = musicFetchStrategyMap.get(command.getMusicFetchSource());
        if (strategy == null) {
            throw new NotImplementedException("不支持的类型：" + command.getMusicFetchSource());
        }
        return strategy.definedAssemblyFlow(command);
    }
}
