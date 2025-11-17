package com.echovale.music.infrastructure.query;

import com.echovale.music.appliaction.query.IMusicQueryService;
import com.echovale.music.appliaction.query.dto.MusicIdMappingDTO;
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
@Service("MusicQueryService")
public class MusicQueryService implements IMusicQueryService {

    @Autowired
    MusicMapper musicMapper;

    @Autowired
    MusicWrapper musicWrapper;

    @Override
    public List<MusicIdMappingDTO> queryMusicDoubleKeyByIds(List<Long> ids) {

        MPJLambdaWrapper<MusicPO> wrapper = musicWrapper.queryMusicDoubleKeyWrapper()
                .eq(MusicPO::getId, ids);

        return musicMapper.selectJoinList(MusicIdMappingDTO.class, wrapper);
    }
}
