package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.AuthorVO;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 21:33
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        imports = {
                com.echovale.music.domain.valueobject.NeteaseId.class,
                com.echovale.music.domain.valueobject.AuthorId.class
        }
)
public abstract class AuthorConverter {

    public Author toAggregate(AuthorDetailResult res) {
        return core(res);
    }

    public AuthorPO toPO(Author author) {
        return core(author);
    }

    public Author toAggregate(AuthorPO authorPO) {
        return core(authorPO);
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getId()))")
    @Mapping(target = "alias", expression = "java(res.getAlias().toString())")
    abstract Author autoMapping(AuthorDetailResult res);


    @Mapping(target = "id", expression = "java(res.getIdValue())")
    @Mapping(target = "neteaseId", expression = "java(res.getNeteaseIdValue())")
    abstract AuthorPO autoMapping(Author res);


    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getNeteaseId()))")
    @Mapping(target = "id", expression = "java(new AuthorId(res.getId()))")
    abstract Author autoMapping(AuthorPO res);



    private Author core(AuthorDetailResult res) {
        return autoMapping(res);
    }

    private AuthorPO core(Author author) {
        return autoMapping(author);
    }

    public Author core(AuthorPO res) {
        return autoMapping(res);
    }


    public AuthorVO toVO(Author author) {
        return core(author, AuthorVO.builder().build());
    }

    public AuthorVO toVO(Author author, AuthorVO authorVO) {
        return core(author, authorVO);
    }


    @Mapping(target = "id", source = "res.id")
    @Mapping(target = "neteaseId", source = "res.neteaseId")
    @Mapping(target = "name", source = "res.name")
    abstract AuthorVO autoMapping(Author res, @MappingTarget AuthorVO target);



    private AuthorVO core(Author author, AuthorVO authorVO) {
        return autoMapping(author, authorVO);
    }




}
