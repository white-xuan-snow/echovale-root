package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.MusicInfoExtendPO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MusicInfoExtMapper extends MPJBaseMapper<MusicInfoExtendPO> {
}
