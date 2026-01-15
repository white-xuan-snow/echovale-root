package com.echovale.music.infrastructure.query;

import com.echovale.music.appliaction.dto.MusicAuthorsDTO;
import com.echovale.music.appliaction.dto.MusicIdGatherDTO;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.echovale.music.infrastructure.converter.MusicLyricConverter;
import com.echovale.music.infrastructure.mapper.LyricMapper;
import com.echovale.music.infrastructure.mapper.MusicAuthorsMapper;
import com.echovale.music.infrastructure.mapper.MusicMapper;
import com.echovale.music.infrastructure.mapper.MusicQualitiesMapper;
import com.echovale.music.infrastructure.po.LyricPO;
import com.echovale.music.infrastructure.po.MusicAuthorsPO;
import com.echovale.music.infrastructure.po.MusicPO;
import com.echovale.music.infrastructure.po.MusicQualitiesPO;
import com.echovale.music.infrastructure.query.wrapper.MusicWrapper;
import com.echovale.shared.utils.ListUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 20:04
 */

@Slf4j
@Service
public class MusicQueryServiceImpl implements MusicQueryService {

    @Autowired
    MusicMapper musicMapper;

    @Autowired
    private MusicWrapper musicWrapper;

    @Autowired
    private MusicQualitiesMapper musicQualitiesMapper;

    @Autowired
    private MusicConverter musicConverter;

    @Autowired
    private LyricMapper lyricMapper;

    @Autowired
    private MusicLyricConverter musicLyricConverter;

    @Autowired
    private MusicAuthorsMapper musicAuthorsMapper;

    @Override
    public List<MusicIdMapping> queryMusicDoubleKeyByIds(List<MusicId> ids) {

        MPJLambdaWrapper<MusicPO> wrapper = musicWrapper.queryMusicIdsWrapper()
                .eq(MusicPO::getId, ids);

        return musicMapper.selectJoinList(MusicIdMapping.class, wrapper);
    }

    @Override
    public Boolean queryMusicExistsByNeteaseId(NeteaseId neteaseId) {

        MPJLambdaWrapper<MusicPO> wrapper = musicWrapper.queryMusicIdByNeteaseIdWrapper(neteaseId.getId());

        return musicMapper.selectCount(wrapper) > 0;
    }

    @Override
    public MusicId queryMusicIdByNeteaseId(NeteaseId neteaseId) {
        return null;

    }

    @Override
    public Music queryMusicByIds(MusicId musicId, NeteaseId neteaseId) {

        MPJLambdaWrapper<MusicPO> musicQueryWrapper = musicWrapper.queryMusicByIdsWrapper(musicId, neteaseId);

        MusicPO musicPO = musicMapper.selectOne(musicQueryWrapper);

        musicId = MusicId.of(musicPO);

        MPJLambdaWrapper<MusicQualitiesPO> musicQualitiesWrapper = musicWrapper.queryMusicQualitiesWrapper(musicId);

        List<MusicQualitiesPO> musicQualitiesPOList = musicQualitiesMapper.selectList(musicQualitiesWrapper);

        log.info("[MusicQueryServiceImpl].[queryMusicByIds] 通过音乐id：{}, netease id: {} 查询结果：{}", musicId.getId(), neteaseId.getId(), musicPO != null);

        return musicConverter.toAggregateByPOS(musicPO, musicQualitiesPOList);
    }

    @Override
    public NeteaseId queryNeteaseIdById(MusicId id) {

        MPJLambdaWrapper<MusicPO> wrapper = musicWrapper.queryNeteaseIdByIdWrapper(id);

        Long neteaseId = musicMapper.selectJoinOne(Long.class, wrapper);

        return new NeteaseId(neteaseId);
    }

    @Override
    public Lyric queryLyricsById(MusicId id) {

        MPJLambdaWrapper<LyricPO> wrapper = musicWrapper.queryMusicLyricsByIdWrapper(id);

        LyricPO lyricPO = lyricMapper.selectOne(wrapper);

        return musicLyricConverter.toAggregate(lyricPO);
    }

    @Override
    public List<NeteaseId> queryNonexistentNeteaseIds(List<NeteaseId> neteaseIds) {

        // 查询
        MPJLambdaWrapper<MusicPO> wrapper = musicWrapper.queryExistentNeteaseIds(neteaseIds);
        List<Long> existentNeteaseIds = musicMapper.selectJoinList(Long.class, wrapper);

        // 数据库未持久化的NeteaseIds
        HashSet<Long> existentNeteaseIdSet = new HashSet<>(existentNeteaseIds);
        List<NeteaseId> nonexistentNeteaseIds = neteaseIds.stream()
                .filter(neteaseId -> !existentNeteaseIdSet.contains(neteaseId.getId()))
                .toList();

        return nonexistentNeteaseIds;
    }

    @Override
    public List<Music> queryMusicByMusicIds(List<MusicId> musicIds) {
        if (CollectionUtils.isEmpty(musicIds)) {
            return Collections.emptyList();
        }

        List<MusicPO> musicPOList = musicMapper.selectList(
                musicWrapper.queryMusicsByMusicIdsWrapper(musicIds)
        );

        List<MusicQualitiesPO> musicQualitiesPOList = musicQualitiesMapper.selectList(
                musicWrapper.queryMusicQualitiesListByMusicIdsWrapper(musicIds)
        );

        // 方法一
        HashMap<Long, List<MusicQualitiesPO>> musicQualitiesMap = new HashMap<>(ListUtils.getInitialCapacity(musicPOList.size()));
        for (MusicQualitiesPO musicQualitiesPO : musicQualitiesPOList) {
            musicQualitiesMap.computeIfAbsent(musicQualitiesPO.getMusicId(), k -> new ArrayList<>()).add(musicQualitiesPO);
        }

        // 方法二
//        Map<Long, List<MusicQualitiesPO>> musicQualitiesMap = musicQualitiesPOList.stream()
//                .collect(Collectors.groupingBy(MusicQualitiesPO::getMusicId));

        // 方法三
//        Map<Long, List<MusicQualitiesPO>> musicQualitiesMap = musicQualitiesPOList.parallelStream()
//                .collect(Collectors.groupingByConcurrent(MusicQualitiesPO::getMusicId));

        // 方法四
//        ListMultimap<Long, MusicQualitiesPO> musicQualitiesMap = Multimaps.index(musicQualitiesPOList, MusicQualitiesPO::getMusicId);


        return musicPOList.stream()
                .map(musicPO -> musicConverter.toAggregateByPOS(musicPO, musicQualitiesMap.get(musicPO.getId())))
                .toList();
    }

    @Override
    public MusicAuthorsDTO queryAuthorIdsByMusicIds(List<MusicId> musicIds) {
        if (CollectionUtils.isEmpty(musicIds)) {
            return new MusicAuthorsDTO();
        }

        List<MusicAuthorsPO> musicAuthorsPOList = musicAuthorsMapper.selectList(
                musicWrapper.queryAuthorIdsByMusicIdsWrapper(musicIds)
        );

        Map<Long, List<Long>> musicLongIdToAuthorLongIdsMap = new HashMap<>(ListUtils.getInitialCapacity(musicIds.size()));

        for (MusicAuthorsPO musicAuthorsPO : musicAuthorsPOList) {
            musicLongIdToAuthorLongIdsMap.computeIfAbsent(
                    musicAuthorsPO.getMusicId(),
                    k -> new ArrayList<>()).add(musicAuthorsPO.getAuthorId()
            );
        }

        Map<MusicId, List<AuthorId>> musicIdToAuthorIdsMap = musicLongIdToAuthorLongIdsMap.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> MusicId.of(e.getKey()),
                        e -> e.getValue().stream().map(AuthorId::of).toList()
                ));


        List<Long> authorLongIds = musicAuthorsPOList.stream()
                .map(MusicAuthorsPO::getAuthorId)
                .toList();

        List<AuthorId> authorIds = AuthorId.byLongIds(authorLongIds);

        return MusicAuthorsDTO.builder()
                .musicIdToAuthorIdsMap(musicIdToAuthorIdsMap)
                .musicLongIdToAuthorLongIdsMap(musicLongIdToAuthorLongIdsMap)
                .musicIdList(musicIds)
                .authorLongIds(authorLongIds)
                .authorIdList(authorIds)
                .build();

    }

    @Override
    public MusicIdGatherDTO queryMusicIdGatherByNeteaseIds(List<NeteaseId> neteaseIds) {
        if (CollectionUtils.isEmpty(neteaseIds)) {
            return MusicIdGatherDTO.EMPTY;
        }

        List<Long> existentNeteaseLongIdList = musicMapper.selectJoinList(
                Long.class,
                musicWrapper.queryExistentNeteaseIds(neteaseIds)
        );

        List<Long> existentMusicLongIdList = musicMapper.selectJoinList(
                Long.class,
                musicWrapper.queryExistentMusicIds(neteaseIds)
        );

        List<MusicId> existentMusicIdList = MusicId.byLongList(existentMusicLongIdList);

        List<NeteaseId> nonexistentNeteaseIds = neteaseIds.stream()
                .filter(neteaseId -> !existentNeteaseLongIdList.contains(neteaseId.getId()))
                .toList();

        return MusicIdGatherDTO.builder()
                .existentMusicIdList(existentMusicIdList)
                .nonexistentMusicNeteaseIdList(nonexistentNeteaseIds)
                .build();
    }

    @Override
    public List<MusicId> queryMusicIdsByNeteaseIds(List<NeteaseId> existentNeteaseIds) {
        if (CollectionUtils.isEmpty(existentNeteaseIds)) {
            return List.of();
        }

        List<Long> existentMusicLongIdList = musicMapper.selectJoinList(
                Long.class,
                musicWrapper.queryExistentMusicIds(existentNeteaseIds)
        );

        return MusicId.byLongList(existentMusicLongIdList);}


}
