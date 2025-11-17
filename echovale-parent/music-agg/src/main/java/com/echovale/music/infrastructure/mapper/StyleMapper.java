package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.StylePO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface StyleMapper extends MPJBaseMapper<StylePO> {
}
