package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.AuthorPO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 23:21
 */

@Mapper
public interface AuthorMapper extends MPJBaseMapper<AuthorPO> {
}
