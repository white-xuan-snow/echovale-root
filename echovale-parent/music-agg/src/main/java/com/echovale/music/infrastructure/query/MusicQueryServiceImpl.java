package com.echovale.music.infrastructure.query;

import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.echovale.music.infrastructure.mapper.MusicMapper;
import com.echovale.music.infrastructure.po.MusicPO;
import com.echovale.music.infrastructure.query.wrapper.MusicWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    MusicWrapper musicWrapper;

    @Autowired
    private MusicConverter musicConverter;

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

        MPJLambdaWrapper<MusicPO> wrapper = musicWrapper.queryMusicByIdsWrapper(musicId, neteaseId);

        MusicPO musicPO = musicMapper.selectOne(wrapper);

        log.info("[MusicQueryServiceImpl].[queryMusicByIds] 通过音乐id：{}, netease id: {} 查询结果：{}", musicId.getId(), neteaseId.getId(), musicPO != null);

        return musicConverter.toAggregate(musicPO);
    }

    @Override
    public NeteaseId queryNeteaseIdById(MusicId id) {

        MPJLambdaWrapper<MusicPO> wrapper = musicWrapper.queryNeteaseIdByIdWrapper(id);

        Long neteaseId = musicMapper.selectJoinOne(Long.class, wrapper);

        return new NeteaseId(neteaseId);
    }
}
