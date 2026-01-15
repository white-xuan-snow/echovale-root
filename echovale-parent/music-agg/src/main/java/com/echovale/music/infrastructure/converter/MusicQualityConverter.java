package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.MusicQualityVO;
import com.echovale.music.domain.entity.MusicQuality;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.music.infrastructure.po.MusicQualitiesPO;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.MusicQualityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @description: TODO
 * @author 30531
 * @date 2025/11/24 10:20
 * @version 1.0
 */


/**
 * MusicQualityConverter 是一个抽象类，使用 MapStruct 进行对象映射转换。
 * 它被配置为 Spring 组件，并引入了 MusicId 类。
 */
@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        imports = {
                MusicId.class
        },
        uses = {
                MusicQualifier.class
        }
)
public interface MusicQualityConverter {


    //    @Mapping(target = "musicId", source = "musicId")
    @Mapping(target = "level", expression = "java(level)")
    MusicQuality toAggregate(MusicQualityDTO res, String level, Long musicId);


    MusicQualityVO toVO(MusicQuality res);


    //    @Mapping(target = "musicId", source = "res.musicId")
//    @Mapping(target = "level", source = "res.level")
    @Mapping(target = "musicId", source = "musicId")
    // TODO MusicId中也存在getId方法，与MusicQuality中的getId方法冲突了
    @Mapping(target = "id", source = "res.id")
    MusicQualitiesPO toPO(MusicQuality res, MusicId musicId);


    /**
     * 将MusicQualitiesPO对象转换为MusicQuality对象的映射方法
     * 该方法使用了MapStruct框架的@Mapping注解进行属性映射配置
     *
     * @param res 源对象，类型为MusicQualitiesPO，包含需要转换的数据
     * @return 转换后的目标对象，类型为MusicQuality
     */
//    @Mapping(target = "level", source = "res.level")
    MusicQuality toAggregate(MusicQualitiesPO res);

}
