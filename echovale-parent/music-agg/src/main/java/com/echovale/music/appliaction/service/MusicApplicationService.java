package com.echovale.music.appliaction.service;

import com.echovale.common.domain.api.exception.ConflictException;
import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.AddMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.gateway.MusicApiGateway;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.appliaction.query.dto.MusicIdMapping;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 17:06
 */

@Slf4j
@Service
public class MusicApplicationService {

    @Autowired
    private MusicApiGateway musicApiGatewayImpl;

    @Autowired
    private MusicRepository musicRepository;


    @Autowired
    private MusicQueryService musicQueryService;
    @Autowired
    private MusicApi music;


    @Autowired
    private AuthorConverter authorConverter;

    public List<MusicUrlVO> elicitMusicUrl(ElicitMusicUrlCommand command) throws Exception {
        List<MusicUrlDetailVO> musicUrlDetailVOList = elicitMusicUrlDetail(command);
        return musicUrlDetailVOList.stream()
                .map(musicUrlDetailResult -> MusicUrlVO.builder()
                        .url(musicUrlDetailResult.getUrl())
                        .build())
                .toList();
    }

    public List<MusicUrlDetailVO> elicitMusicUrlDetail(ElicitMusicUrlCommand command) throws Exception {

        List<MusicId> musicIds = new ArrayList<>();
        List<NeteaseId> neteaseIds = new ArrayList<>();

        for (Long id : command.getIds()) {
            musicIds.add(new MusicId(id));
        }
        for (Long id : command.getNeteaseIds()) {
            neteaseIds.add(new NeteaseId(id));
        }

        if (command.withoutNeteaseIds()) {

            List<MusicIdMapping> musicIdMappingList = musicQueryService.queryMusicDoubleKeyByIds(musicIds);

            neteaseIds = musicIdMappingList.stream()
                    .map(MusicIdMapping::getNeteaseId)
                    .toList();

        } else {

            musicIds = MusicId.getEmptyList(neteaseIds.size());

        }

        return  musicApiGatewayImpl.elicitMusicUrl(musicIds, neteaseIds, command.getLevel());
    }

    public MusicVO addMusic(AddMusicCommand command) throws Exception {

        NeteaseId neteaseId = new NeteaseId(command.getId());

        // 通过neteaseId查询Music
        Boolean isMusicExist = musicQueryService.queryMusicExistsByNeteaseId(neteaseId);


        if (isMusicExist) {
            throw new ConflictException("音乐已存在");
        }

        // 添加音乐
        MusicDetailResult musicDetailResult = musicApiGatewayImpl.elicitMusic(neteaseId);

        // 先转换为Author
        List<Author> authors = musicDetailResult.getAr().stream()
                .map(apiAuthor -> authorConverter.byApi(apiAuthor))
                .toList();

        // Author持久化


        return null;
    }
}
