package com.echovale.service.mapping;

import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.MusicInfoExtendPO;
import com.echovale.domain.po.MusicQualitiesPO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicDetailDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapperConfig.class,
        componentModel = "spring",
        uses = {MusicQualitiesDTO2POMapping.class,
                Author2POMapping.class,
                Album2POMapping.class,})
public interface MusicDetailDTO2ModelMapping {

    MusicDetailDTO2ModelMapping INSTANCE = Mappers.getMapper(MusicDetailDTO2ModelMapping.class);

    // 自动映射
    @Mapping(source = "dto.al", target = "album")
    @Mapping(source = "dto.ar", target = "authors")
    void autoMapping(MusicDetailDTO dto, @MappingTarget MusicModel model);

    default MusicModel toModel(MusicDetailDTO dto) {
        MusicModel model = MusicModel.builder()
                .neteaseId(dto.getId())
                .qualities(combineQualities(dto))
                .coverType(dto.getOriginCoverType())
                .mvId(Long.parseLong(dto.getMv()))
                .info(mapExtendInfo(dto))
                .build();

        autoMapping(dto, model);

        return model;
    }


    // 音乐扩展信息
    default MusicInfoExtendPO mapExtendInfo(MusicDetailDTO dto) {
        return MusicInfoExtendPO.builder()
                .musicId(dto.getId())
                .no(dto.getNo())
                .publishTime(dto.getPublishTime())
                .build();
    }

    // 音质转换
    default List<MusicQualitiesPO> combineQualities(MusicDetailDTO dto) {
        List<MusicQualitiesPO> res = new ArrayList<>();

        // 低品质
        MusicQualitiesPO l = MusicQualitiesDTO2POMapping.INSTANCE.toPO(dto.getL());
        l.setId(dto.getId());
        res.add(l);

        // 中品质
        MusicQualitiesPO m = MusicQualitiesDTO2POMapping.INSTANCE.toPO(dto.getM());
        m.setId(dto.getId());
        res.add(m);

        // 高品质
        MusicQualitiesPO h = MusicQualitiesDTO2POMapping.INSTANCE.toPO(dto.getH());
        h.setId(dto.getId());
        res.add(h);

        // 无损
        MusicQualitiesPO sq = MusicQualitiesDTO2POMapping.INSTANCE.toPO(dto.getSq());
        sq.setId(dto.getId());
        res.add(sq);

        // Hires
        MusicQualitiesPO hr = MusicQualitiesDTO2POMapping.INSTANCE.toPO(dto.getHr());
        hr.setId(dto.getId());
        res.add(hr);

        return res;
    }
}

