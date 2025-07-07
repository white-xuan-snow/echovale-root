package com.echovale.service.mapping;

import com.echovale.common.utils.StringUtils;
import com.echovale.domain.po.PlaylistPO;
import com.echovale.service.config.MappingConfig;
import com.echovale.service.dto.MusicDTO;
import com.echovale.service.vo.PlaylistVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/7 22:50
 */


@Mapper(config = MappingConfig.class,
        componentModel = "spring")
public abstract class PlaylistVOMapping {

    @Autowired
    StringUtils stringUtils;


    @Mapping(target = "tags", ignore = true)
    abstract PlaylistVO autoPOMapping(PlaylistPO po, @MappingTarget PlaylistVO vo);


    public PlaylistVO byPO(PlaylistPO po) {
        return corePO(po, PlaylistVO.builder().build());
    }

    public PlaylistVO byPO(PlaylistPO po, PlaylistVO vo) {
        return corePO(po, vo);
    }


    private PlaylistVO corePO(PlaylistPO po, PlaylistVO vo) {
        autoPOMapping(po, vo);
        vo.setTags(stringUtils.string2List(po.getTags(), Objects::toString));
        return vo;
    }

    public PlaylistVO addMusicDTOList(List<MusicDTO> dtoList) {
        return coreDTOList(dtoList, PlaylistVO.builder().build());
    }

    public PlaylistVO addMusicDTOList(List<MusicDTO> dtoList, PlaylistVO vo) {
        return coreDTOList(dtoList, vo);
    }

    private PlaylistVO coreDTOList(List<MusicDTO> dtoList, PlaylistVO vo) {
        vo.setMusics(dtoList);
        return vo;
    }


}
