package com.echovale.music.appliaction.service;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.appliaction.query.AlbumQueryService;
import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.service.MusicSupplyService;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MusicApplicationServiceImpl implements MusicApplicationService {

    @Autowired
    private MusicApiGateway musicApiGatewayImpl;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicSupplyService musicSupplyService;


    @Autowired
    private MusicQueryService musicQueryService;
    @Autowired
    private MusicApi music;


    @Autowired
    private AuthorConverter authorConverter;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorQueryService authorQueryService;

    @Autowired
    private MusicConverter musicConverter;

    @Autowired
    private AlbumConverter albumConverter;


    @Autowired
    private AlbumQueryService albumQueryService;

    @Override
    public List<MusicUrlVO> elicitMusicUrl(ElicitMusicUrlCommand command) throws Exception {
        List<MusicUrlDetailVO> musicUrlDetailVOList = elicitMusicUrlDetail(command);
        return musicUrlDetailVOList.stream()
                .map(musicUrlDetailResult -> MusicUrlVO.builder()
                        .url(musicUrlDetailResult.getUrl())
                        .build())
                .toList();
    }


    @Override
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

    @Override
    public MusicVO playMusic(PlayMusicCommand command) throws Exception {

        NeteaseId neteaseId = new NeteaseId(command.getNeteaseId());

        MusicId musicId = new MusicId(command.getId());

        MusicDTO musicDTO = musicSupplyService.getMusic(musicId, neteaseId);

        return musicConverter.toVO(musicDTO);
    }

}
