package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.AlbumPO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlbumMapper extends MPJBaseMapper<AlbumPO> {
}
