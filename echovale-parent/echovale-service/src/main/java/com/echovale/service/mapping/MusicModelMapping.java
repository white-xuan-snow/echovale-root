package com.echovale.service.mapping;

import com.echovale.domain.model.MusicModel;
import com.echovale.domain.po.AlbumPO;
import com.echovale.domain.po.MusicInfoExtendPO;
import com.echovale.domain.po.MusicPO;
import com.echovale.domain.po.MusicQualitiesPO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.ChorusDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicDetailDTO;
import org.mapstruct.*;
import com.echovale.service.config.MappingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        uses = {MusicQualitiesDTO2POMapping.class,
                Author2POMapping.class,
                Album2POMapping.class,})
public abstract class MusicModelMapping {

    @Autowired
    MusicQualitiesDTO2POMapping musicQualitiesDTO2POMapping;


    /*
    MusicDetailDTO转MusicModel
    */
    // 自动匹配映射
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dto.al", target = "album")
    @Mapping(source = "dto.ar", target = "authors")
    abstract void detailAutoMapping(MusicDetailDTO dto, @MappingTarget MusicModel model);

    public MusicModel detailToModel(MusicDetailDTO dto) {
        return detailCore(dto, new MusicModel());
    }
    public MusicModel detailToModel(MusicDetailDTO dto, MusicModel model) {
        return detailCore(dto, model);
    }

    public abstract void poToModel(MusicPO po, @MappingTarget MusicModel model);

    @Mapping(source = "po.id", target = "album.id")
    public abstract void albumToModel(AlbumPO po, @MappingTarget MusicModel model);

    private MusicModel detailCore(MusicDetailDTO dto, MusicModel model) {

        model.setNeteaseId(dto.getId());
        model.setQualities(detailQualities(dto));
        model.setCoverType(dto.getOriginCoverType());
        model.setMvId(Long.parseLong(dto.getMv()));
        model.setInfo(detailExtendInfo(dto));

        detailAutoMapping(dto, model);

        return model;
    }



    // 音乐扩展信息
    private MusicInfoExtendPO detailExtendInfo(MusicDetailDTO dto) {
        return MusicInfoExtendPO.builder()
                .musicId(dto.getId())
                .no(dto.getNo())
                .publishTime(dto.getPublishTime())
                .build();
    }

    // 音质转换
    private List<MusicQualitiesPO> detailQualities(MusicDetailDTO dto) {
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

    /*
    ChorusDTO转MusicModel
    */

    public MusicModel chorusToModel(ChorusDTO dto) {
        return chorusCore(dto, new MusicModel());
    }
    public MusicModel chorusToModel(ChorusDTO dto, MusicModel model) {
        return chorusCore(dto, model);
    }

    private MusicModel chorusCore(ChorusDTO dto, MusicModel model) {

        model.setChorus(List.of(
                dto.getStartTime(),
                dto.getEndTime()
        ).toString());

        return model;
    }
}

