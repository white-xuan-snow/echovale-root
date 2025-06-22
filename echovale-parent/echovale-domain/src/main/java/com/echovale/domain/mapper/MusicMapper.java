package com.echovale.domain.mapper;

import com.echovale.domain.po.MusicPO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
public interface MusicMapper extends MPJBaseMapper<MusicPO> {
}
