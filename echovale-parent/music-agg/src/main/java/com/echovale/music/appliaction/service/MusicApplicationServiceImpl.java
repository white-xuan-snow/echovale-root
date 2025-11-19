package com.echovale.music.appliaction.service;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.gateway.MusicApiGateway;
import com.echovale.music.appliaction.query.AlbumQueryService;
import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.appliaction.query.dto.MusicIdMapping;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.domain.repository.MusicRepository;
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

        // 尝试使用id或neteaseId查询音乐

        NeteaseId neteaseId = new NeteaseId(command.getNeteaseId());

        MusicId musicId = new MusicId(command.getId());

        Music music = musicQueryService.queryMusicByIds(musicId, neteaseId);


        if (music.getId() == null) {

            // 外部获取MusicDetailResult
            MusicDetailResult musicDetailResult = musicApiGatewayImpl.elicitMusic(neteaseId);

            // 转换为Music聚合根
            Music musicFromNetease = musicConverter.toAggregate(musicDetailResult);

            // 转换为Author聚合根
            List<Author> authors = musicDetailResult.getAr().stream()
                    .map(authorConverter::toAggregate)
                    .toList();

            // 转换为Album聚合根

            Album album = albumConverter.toAggregate(musicDetailResult.getAl());

            // 转换为MusicVO


        }


        List<Author> authors = authorQueryService.queryAuthorsByMusicId(music.getId());

        // 查询专辑
        Album album = albumQueryService.queryAlbumById(music.getAlbumId());

        // 组装
        MusicVO musicVO = musicConverter.byAggregates(music, authors, album);



        return null;
    }



}
