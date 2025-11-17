package com.echovale.music.appliaction.service;

import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.query.IMusicQueryService;
import com.echovale.music.appliaction.query.dto.MusicIdMappingDTO;
import com.echovale.music.domain.service.MusicUrlProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 17:06
 */

@Slf4j
@Service
public class ElicitMusicUrlApplicationService {

    @Autowired
    private MusicUrlProvider musicUrlProvider;

    @Autowired @Qualifier("MusicQueryService")
    private IMusicQueryService musicQueryService;

    public List<String> elicitMusicUrl(ElicitMusicUrlCommand command) throws Exception {

        if (command.hasNeteaseIds()) {
            return musicUrlProvider.elicitMusicUrl(command.getNeteaseIds(), command.getLevel());
        } else {
            List<MusicIdMappingDTO> musicIdMappingDTOS = musicQueryService.queryMusicDoubleKeyByIds(command.getIds());
            List<Long> neteaseIds = musicIdMappingDTOS.stream()
                    .map(MusicIdMappingDTO::getNeteaseId)
                    .toList();
            return musicUrlProvider.elicitMusicUrl(neteaseIds, command.getLevel());
        }


    }
}
