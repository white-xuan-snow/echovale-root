package com.echovale.music.infrastructure.mapper;

import com.echovale.music.infrastructure.po.AlbumAuthorsPO;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface AlbumAuthorsMapper extends MppBaseMapper<AlbumAuthorsPO> {
}
