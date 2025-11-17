package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.MusicAwardsPO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MusicAwardsMapper extends MPJBaseMapper<MusicAwardsPO> {
}
