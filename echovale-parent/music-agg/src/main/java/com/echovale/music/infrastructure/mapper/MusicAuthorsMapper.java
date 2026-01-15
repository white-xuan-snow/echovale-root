package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.MusicAuthorsPO;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MusicAuthorsMapper extends MPJBaseMapper<MusicAuthorsPO> {
}
