package com.echovale.playlist.infrastructure.mapper;

import com.echovale.playlist.infrastructure.po.PlaylistPO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaylistMapper extends MPJBaseMapper<PlaylistPO> {
}
