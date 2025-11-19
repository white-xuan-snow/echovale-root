package com.echovale.music.infrastructure.query;

import com.echovale.music.appliaction.query.AlbumQueryService;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.infrastructure.converter.AlbumConverter;
import com.echovale.music.infrastructure.mapper.AlbumMapper;
import com.echovale.music.infrastructure.po.AlbumPO;
import com.echovale.music.infrastructure.query.wrapper.AlbumWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 20:29
 */

@Slf4j
@Service
public class AlbumQueryServiceImpl implements AlbumQueryService {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumWrapper albumWrapper;

    @Autowired
    private AlbumConverter albumConverter;


    @Override
    public Album queryAlbumById(AlbumId albumId) {

        MPJLambdaWrapper<AlbumPO> wrapper = albumWrapper.queryAlbumById(albumId);

        AlbumPO albumPO = albumMapper.selectOne(wrapper);

        log.info("[AlbumQueryServiceImpl].[queryAlbumById] 通过专辑id: {} 查询结果：{}", albumId.getId(), albumPO != null);

        return albumConverter.toAggregate(albumPO);
    }
}
