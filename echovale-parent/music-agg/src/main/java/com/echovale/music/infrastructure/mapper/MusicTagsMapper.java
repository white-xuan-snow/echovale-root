package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.MusicTagsPO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MusicTagsMapper extends MPJBaseMapper<MusicTagsPO> {
}
