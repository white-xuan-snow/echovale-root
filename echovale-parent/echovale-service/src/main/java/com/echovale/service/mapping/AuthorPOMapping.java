package com.echovale.service.mapping;

import com.echovale.domain.po.AuthorPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Author;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class,
        componentModel = "spring")
public abstract class AuthorPOMapping {

    @Mapping(source = "author.id", target = "neteaseId")
    @Mapping(target = "id", ignore = true)
    public abstract AuthorPO byAuthor(Author author);

    public AuthorPO byDetailRes(AuthorDetailResult res) {
        return coreDetailRes(res, AuthorPO.builder().build());
    }

    public AuthorPO byDetailRes(AuthorDetailResult res, AuthorPO po) {
        return coreDetailRes(res, po);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "res.id", target = "neteaseId")
    @Mapping(target = "alias", ignore = true)
    abstract AuthorPO autoDetailResMapping(AuthorDetailResult res, @MappingTarget AuthorPO po);

    private AuthorPO coreDetailRes(AuthorDetailResult res, AuthorPO po) {
        // 翻译名
        po.setTransName(res.toString());
        // 别名
        po.setAlias(res.getAlias().toString());
        // 封面url
        po.setCoverUrl(res.getCover());
        // 头像url
        po.setAvatarUrl(res.getAvatar());
        // 简介
        po.setDescription(res.getBriefDesc());
        return po;
    }

    // TODO 只做了id映射，因为业务不需要其它字段
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "res.id", target = "neteaseId")
    @Mapping(target = "alias", ignore = true)
    abstract AuthorPO autoResultMapping(AuthorResult res, @MappingTarget AuthorPO po);

    public AuthorPO byResult(AuthorResult res) {
        return coreResult(res, AuthorPO.builder().build());
    }

    public AuthorPO byResult(AuthorResult res, AuthorPO po) {
        return coreResult(res, po);
    }

    private AuthorPO coreResult(AuthorResult res, AuthorPO po) {
        autoResultMapping(res, po);
        po.setMusicSize(null);
        po.setAlbumSize(null);
        return po;
    }


}
