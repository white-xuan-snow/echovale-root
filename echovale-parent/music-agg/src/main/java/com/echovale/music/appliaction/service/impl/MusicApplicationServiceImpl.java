package com.echovale.music.appliaction.service.impl;

import com.echovale.shared.infrastructure.utils.StreamUtils;
import com.echovale.music.api.vo.LyricVO;
import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.ElicitMusicLyricCommand;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.command.PlayMusicsCommand;
import com.echovale.music.appliaction.dto.MusicAuthorsDTO;
import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.appliaction.dto.MusicIdGatherDTO;
import com.echovale.music.appliaction.model.ExternalMusicMapHolder;
import com.echovale.music.appliaction.service.AlbumApplicationService;
import com.echovale.music.appliaction.service.AuthorApplicationService;
import com.echovale.music.appliaction.service.MusicApplicationService;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.appliaction.query.AlbumQueryService;
import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.service.MusicSupplyService;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.echovale.music.infrastructure.converter.MusicLyricConverter;
import com.echovale.shared.infrastructure.utils.ListUtil;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 17:06
 */

@Slf4j
@Service
@Validated
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
    private MusicLyricConverter musicLyricConverter;


    @Autowired
    private AlbumQueryService albumQueryService;


    @Autowired
    private AuthorApplicationService authorApplicationService;

    @Autowired
    private AlbumApplicationService albumApplicationService;

    @Autowired
    private MusicApiGateway musicApiGateway;


    @Override
    public List<MusicUrlVO> elicitMusicUrl(@Validated ElicitMusicUrlCommand command) {
        List<MusicUrlDetailVO> musicUrlDetailVOList = elicitMusicUrlDetail(command);
        return musicUrlDetailVOList.stream()
                .map(musicUrlDetailResult -> MusicUrlVO.builder()
                        .url(musicUrlDetailResult.getUrl())
                        .build())
                .toList();
    }


    @Override
    public List<MusicUrlDetailVO> elicitMusicUrlDetail(@Validated ElicitMusicUrlCommand command) {

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
    public MusicVO playMusic(PlayMusicCommand command) {

        NeteaseId neteaseId = new NeteaseId(command.getNeteaseId());

        MusicId musicId = new MusicId(command.getId());

        MusicDTO musicDTO = musicSupplyService.createAndSaveMusicFromExternal(musicId, neteaseId);

        return musicConverter.toVO(musicDTO);
    }


    public List<MusicVO> playMusics(PlayMusicsCommand command) {
        if (command.withoutIds()) {
            List<NeteaseId> neteaseIds = NeteaseId.byLongList(command.getNeteaseIds());

            MusicIdGatherDTO musicIdGatherDTO = musicQueryService.queryMusicIdGatherByNeteaseIds(neteaseIds);

            List<MusicVO> existentMusicVOList = assembleMusicsNative(musicIdGatherDTO.getExistentMusicIdList());

            List<MusicVO> nonExistentMusicVOList = assembleMusicsExternal(musicIdGatherDTO.getNonexistentMusicNeteaseIdList());

            return ListUtil.concat(existentMusicVOList, nonExistentMusicVOList);
        } else {
            List<MusicId> musicIds = MusicId.byPlayMusicsCommand(command);

            List<MusicVO> musicVOList = assembleMusicsNative(musicIds);

            return musicVOList;
        }
    }

    /**
     * @description: 内部方法根据音乐NeteaseID从外部获取数据组装MusicVO
     * @param: nonexistentMusicNeteaseIdList
     * @return: java.util.List<com.echovale.music.api.vo.MusicVO>
     * @author 30531
     * @date: 2025/12/26 14:04
     */
    private List<MusicVO> assembleMusicsExternal(List<NeteaseId> nonexistentMusicNeteaseIdList) {

        List<MusicDetailResult> tracks = musicSupplyService.obtainMusicDetailResults(nonexistentMusicNeteaseIdList);

        return saveAndQueryMusicsByExternalTracks(tracks);
    }




    /**
     * @description: 内部方法根据音乐ID从内部查询组装MusicVO
     * @param: musicIds
     * @return: java.util.List<com.echovale.music.api.vo.MusicVO>
     * @author 30531
     * @date: 2025/12/26 14:02
     */
    private List<MusicVO> assembleMusicsNative(List<MusicId> musicIds) {
        if (CollectionUtils.isEmpty(musicIds)) {
            return Collections.emptyList();
        }

        List<Music> musicList = musicQueryService.queryMusicByMusicIds(musicIds);

        List<AlbumId> albumIds = AlbumId.byMusics(musicList);
        List<Album> albumList = albumApplicationService.fetchAlbumsByAlbumIds(albumIds);

        MusicAuthorsDTO musicAuthorsDTO = musicQueryService.queryAuthorIdsByMusicIds(musicIds);

        List<AuthorId> authorIds = musicAuthorsDTO.getAuthorIdList();
        List<Author> authorList = authorApplicationService.fetchAuthorsByAuthorIds(authorIds);


        Map<Long, Album> albumLongIdToAlbumMap = albumList.stream()
                .collect(Collectors.toMap(a -> a.getId().getId(), a -> a));

        Map<Long, Author> authorLongIdToAuthorMap = authorList.stream()
                .collect(Collectors.toMap(a -> a.getId().getId(), a -> a));

        Map<Long, List<Long>> relationLongMap = musicAuthorsDTO.getMusicLongIdToAuthorLongIdsMap();

        return musicList.stream()
                .map(music -> {
                    Album album = albumLongIdToAlbumMap.getOrDefault(music.getAlbumIdValue(), Album.EMPTY);
                    List<Long> authorLongIds = relationLongMap.getOrDefault(music.getId().getId(), List.of());
                    List<Author> authors = authorLongIds.stream()
                            .map(authorLongIdToAuthorMap::get)
                            .filter(Objects::nonNull)
                            .toList();
                    return musicConverter.toVO(music, authors, album);
                })
                .toList();
    }


    @Override
    public LyricVO elicitLyrics(ElicitMusicLyricCommand command) {

        // 从数据库查询
        if (command.isIdExist()) {
            MusicId id = MusicId.of(command.getId());
            Lyric lyric = musicQueryService.queryLyricsById(id);
            return musicLyricConverter.toVO(lyric, command.getTypes());
        }

        // 获取歌词
        NeteaseId neteaseId = NeteaseId.byLong(command.getNeteaseId());
        Lyric lyric = musicSupplyService.createAndSaveLyricFromExternal(neteaseId);

        return musicLyricConverter.toVO(lyric, command.getTypes());
    }



    /**
     * @description:
     * @param: tracks
     * @return: java.util.List<com.echovale.music.api.vo.MusicVO>
     * @author 30531
     * @date: 2025/12/26 14:05
     */

    @Override
    public List<MusicVO> saveAndQueryMusicsByExternalTracks(List<MusicDetailResult> tracks) {
        if (CollectionUtils.isEmpty(tracks)) {
            return List.of();
        }

        /*
          先分割tracks、authors为已持久化部分与未持久化部分
          已持久化部分：TODO 当前保持为输入数据，可改进为从数据库查询
          未持久化部分：整理去重后从外部分获取
          合并
          重新匹配映射
         */

        ExternalMusicMapHolder mapHolder = ExternalMusicMapHolder.from(tracks);


        // Music NeteaseId, Author NeteaseIds
        HashMap<Long, List<Long>> musicToAuthorsNeteaseMap = new HashMap<>();

        for (MusicDetailResult track : tracks) {
            musicToAuthorsNeteaseMap.put(track.getId(), track.getAr().stream().map(AuthorDetailResult::getId).toList());
        }

        List<NeteaseId> neteaseIds = NeteaseId.byLong(tracks);

        // 通过Music NeteaseId查询未持久化的NeteaseIds
        List<NeteaseId> nonexistentNeteaseIds = musicQueryService.queryNonexistentNeteaseIds(neteaseIds);


        // 划分需要持久化的tracks
        HashSet<NeteaseId> nonexistentNeteaseIdSet = new HashSet<>(nonexistentNeteaseIds);
        List<MusicDetailResult> persistentTracks = tracks.stream()
                .filter(track -> nonexistentNeteaseIdSet.contains(NeteaseId.byLong(track)))
                .toList();

        List<MusicDetailResult> existentTracks = tracks.stream()
                .filter(track -> !nonexistentNeteaseIdSet.contains(NeteaseId.byLong(track)))
                .toList();

        // Author
        List<AuthorDetailResult> distinctAuthorDetailResults = persistentTracks.stream()
                .flatMap(track -> track.getAr().stream())
                // 去重
//                .peek(author -> log.info("[MusicSupplyServiceImpl].[createAndSaveMusics] distinctAuthorDetailResults Author 去重 ID：{}", author.getId()))
                .filter(StreamUtils.distinctByKey(AuthorDetailResult::getId))
                .toList();

        // Author 持久化
        List<Author> persistentAuthors = authorApplicationService.saveAndQueryAuthorsByExternalDetailResult(distinctAuthorDetailResults);

        // Author 合并
        List<Author> existentAuthors = existentTracks.stream()
                .flatMap(track -> track.getAr().stream())
                .map(authorConverter::byDetailResult)
                .toList();
        List<Author> totalAuthors = ListUtil.concat(existentAuthors, persistentAuthors);

        HashMap<Long, Author> authorLongNeteaseIdMap = new HashMap<>();
        for (Author author : totalAuthors) {
            authorLongNeteaseIdMap.put(author.getNeteaseId().getId(), author);
        }


        List<Music> persistentMusics = persistentTracks.stream()
                .map(musicConverter::byInsufficientDetailResult)
                .toList();
        List<ChorusResult> chorusResults = musicApiGateway.elicitChoruses(nonexistentNeteaseIds);

        List<NeteaseId> existentMusicNeteaseIds = NeteaseId.byLong(existentTracks);



        // NeteaseId, Index
        HashMap<Long, Integer> chorusListNeteaseIdMap = new HashMap<>();

        for (int i = 0; i < chorusResults.size(); i++) {
            chorusListNeteaseIdMap.put(chorusResults.get(i).getId(), i);
        }

        for (int i = 0; i < persistentMusics.size(); i++) {
            long originNeteaseId = nonexistentNeteaseIds.get(i).getId();
            int chorusIndex = chorusListNeteaseIdMap.getOrDefault(originNeteaseId, 0);
            if (chorusIndex == 0) {
                continue;
            }
            musicConverter.addChorus(chorusResults.get(chorusIndex), persistentMusics.get(i));
        }


        // 持久化
        persistentMusics = musicRepository.saveAll(persistentMusics);

        List<Music> existMusics = tracks.stream()
                .filter(track -> !nonexistentNeteaseIdSet.contains(NeteaseId.byLong(track)))
                .map(musicConverter::byDetailResult)
                .toList();
        List<Music> totalMusics = ListUtil.concat(existMusics, persistentMusics);


        List<AlbumResult> albumResults = persistentTracks.stream()
                .map(MusicDetailResult::getAl)
                .toList();
        List<Album> existentAlbumResults = existentTracks.stream()
                .map(MusicDetailResult::getAl)
                .map(albumConverter::byInsufficientResult)
                .toList();

        List<Album> persistentAlbums = albumApplicationService.saveAndQueryAlbumsByExternalResult(albumResults);

        List<Album> totalAlbums = ListUtil.concat(existentAlbumResults, persistentAlbums);


        HashMap<Long, Album> albumNeteaseLongIdMap = new HashMap<>();
        for (Album album : totalAlbums) {
            albumNeteaseLongIdMap.put(album.getNeteaseId().getId(), album);
        }






        // TODO 合并

        List<MusicDTO> musicDTOList = new ArrayList<>();
        for (int i = 0; i < totalMusics.size(); i++) {
            List<Author> authorList = new ArrayList<>();
            List<Long> authorLongNeteaseIds = musicToAuthorsNeteaseMap.get(totalMusics.get(i).getNeteaseId().getId());
            for (Long authorNeteaseId : authorLongNeteaseIds) {
                authorList.add(authorLongNeteaseIdMap.get(authorNeteaseId));
                   }
            musicDTOList.add(musicConverter.toDTO(totalMusics.get(i), authorList, null));
        }


        return List.of();
    }



    public List<MusicVO> assembleMusicsNativeByNetease(List<NeteaseId> existentNeteaseIds) {
        if (CollectionUtils.isEmpty(existentNeteaseIds)) {
            return List.of();
        }

        // 查询musicIdList
        List<MusicId> musicIdList = musicQueryService.queryMusicIdsByNeteaseIds(existentNeteaseIds);

        return assembleMusicsNative(musicIdList);
    }




}
