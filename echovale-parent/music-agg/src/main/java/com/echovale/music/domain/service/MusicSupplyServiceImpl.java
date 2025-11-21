package com.echovale.music.domain.service;

import com.echovale.music.appliaction.dto.AlbumDTO;
import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/20 14:15
 */

@Slf4j
@Service
public class MusicSupplyServiceImpl implements MusicSupplyService {

    @Autowired
    private AlbumSupplyService albumSupplyService;

    @Autowired
    private AuthorSupplyService authorSupplyService;



    @Autowired
    private MusicQueryService musicQueryService;

    @Autowired
    private MusicApiGateway musicApiGateway;

    @Autowired
    private MusicConverter musicConverter;

    @Autowired
    private AlbumConverter albumConverter;

    @Autowired
    private AuthorConverter authorConverter;


    @Autowired
    private MusicRepository musicRepository;


    @Override
    public MusicDTO getMusic(MusicId musicId, NeteaseId neteaseId) throws Exception {

        Music music = musicQueryService.queryMusicByIds(musicId, neteaseId);

        Album album;
        List<Author> authorList;

        if (music == null) {
            log.info("[MusicSupplyServiceImpl].[getMusic] 从外部获取Music");

            MusicDetailResult musicDetailResult = musicApiGateway.elicitMusic(neteaseId);
            ChorusResult chorusResult = musicApiGateway.elicitChorus(neteaseId);


            music = musicConverter.toAggregate(musicDetailResult);
            music = musicConverter.addChorus(music, chorusResult);

            // 持久化
            music = musicRepository.save(music);

            album = albumConverter.byAlbumResult(musicDetailResult.getAl());

            authorList = musicDetailResult.getAr().stream()
                    .map(authorConverter::byDetailResult)
                    .toList();

        } else {

            album = albumSupplyService.getAlbum(music.getId(), music.getNeteaseId(), music.getAlbumId());

            authorList = authorSupplyService.getAuthorList(music.getId(), music.getNeteaseId(), music.getAuthorIds());

        }

        return musicConverter.toAggregate(music, authorList, album);
    }
}
