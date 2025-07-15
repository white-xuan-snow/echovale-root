package com.echovale.service.mapping;

import com.echovale.common.constants.entity.LyricConstant;
import com.echovale.domain.po.LyricPO;
import com.echovale.service.config.MappingConfig;
import com.echovale.service.vo.LyricVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/15 15:23
 */

@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        imports = {com.echovale.common.constants.entity.LyricConstant.class}
)
public abstract class LyricVOMapping {

    @Mapping(target = "neteaseLrc", conditionExpression = "java(allowFields.contains(LyricConstant.NETEASE_LRC))")
    @Mapping(target = "neteaseTlrc", conditionExpression = "java(allowFields.contains(LyricConstant.NETEASE_TLRC))")
    @Mapping(target = "neteaseYrc", conditionExpression = "java(allowFields.contains(LyricConstant.NETEASE_YRC))")
    @Mapping(target = "neteaseRomalyc", conditionExpression = "java(allowFields.contains(LyricConstant.NETEASE_ROMALYC))")
    @Mapping(target = "neteaseKlrc", conditionExpression = "java(allowFields.contains(LyricConstant.NETEASE_KLRC))")
    @Mapping(target = "amllTtml", conditionExpression = "java(allowFields.contains(LyricConstant.AMLL_TTML))")
    @Mapping(target = "echoTtml", conditionExpression = "java(allowFields.contains(LyricConstant.ECHO_TTML))")
    @Mapping(source = "po.musicId", target = "id")
    abstract LyricVO autoPOMapping(LyricPO po, @MappingTarget LyricVO vo, HashSet<String> allowFields);


    public LyricVO byPO(LyricPO po) {
        return corePO(po, LyricVO.builder().build(), LyricConstant.ALL_FIELDS);
    }

    public LyricVO byPO(LyricPO po, LyricVO vo) {
        return corePO(po, vo, LyricConstant.ALL_FIELDS);
    }

    public LyricVO byPO(LyricPO po, HashSet<String> allowFields) {
        return corePO(po, LyricVO.builder().build(), allowFields);
    }

    public LyricVO byPO(LyricPO po, LyricVO vo, HashSet<String> allowFields) {
        return corePO(po, vo, allowFields);
    }


    private LyricVO corePO(LyricPO po, LyricVO vo, HashSet<String> allowFields) {
        autoPOMapping(po, vo, allowFields);
        return vo;
    }

}
