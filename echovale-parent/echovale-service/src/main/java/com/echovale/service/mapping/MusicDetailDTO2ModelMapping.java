package com.echovale.service.mapping;

import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.MusicInfoExtendPO;
import com.echovale.domain.po.MusicQualitiesPO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicDetailDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.MusicQuality;
import jakarta.annotation.Resource;
import org.mapstruct.*;
import com.echovale.service.config.MappingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Mapper(config = MappingConfig.class,
        componentModel = "spring",

        uses = {MusicQualitiesDTO2POMapping.class,
                Author2POMapping.class,
                Album2POMapping.class,})
@Component
public abstract class MusicDetailDTO2ModelMapping {


    @Autowired
    MusicQualitiesDTO2POMapping musicQualitiesDTO2POMapping;


    // 自动映射
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dto.al", target = "album")
    @Mapping(source = "dto.ar", target = "authors")
    abstract void autoMapping(MusicDetailDTO dto, @MappingTarget MusicModel model);

    public MusicModel toModel(MusicDetailDTO dto) {
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
    private MusicInfoExtendPO mapExtendInfo(MusicDetailDTO dto) {
        return MusicInfoExtendPO.builder()
                .musicId(dto.getId())
                .no(dto.getNo())
                .publishTime(dto.getPublishTime())
                .build();
    }

    // 音质转换
    private List<MusicQualitiesPO> combineQualities(MusicDetailDTO dto) {
        List<MusicQualitiesPO> res = new ArrayList<>();



        // 低品质
        MusicQualitiesPO l = musicQualitiesDTO2POMapping.toPO(dto.getL());
        if (l != null) {
            l.setId(dto.getId());
            res.add(l);
        }



        // 中品质
        MusicQualitiesPO m = musicQualitiesDTO2POMapping.toPO(dto.getM());
        if (m != null) {
            m.setId(dto.getId());
            res.add(m);
        }


        // 高品质
        MusicQualitiesPO h = musicQualitiesDTO2POMapping.toPO(dto.getH());
        if (h != null) {
            h.setId(dto.getId());
            res.add(h);
        }

        // 无损
        MusicQualitiesPO sq = musicQualitiesDTO2POMapping.toPO(dto.getSq());
        if (sq != null) {
            sq.setId(dto.getId());
            res.add(sq);
        }


        // Hires
        MusicQualitiesPO hr = musicQualitiesDTO2POMapping.toPO(dto.getHr());
        if (hr != null) {
            hr.setId(dto.getId());
            res.add(hr);
        }

        return res;
    }
}

