package com.echovale.service.mapping;

import com.echovale.service.dto.MusicDTO;
import com.echovale.domain.po.AlbumPO;
import com.echovale.domain.po.MusicInfoExtendPO;
import com.echovale.domain.po.MusicPO;
import com.echovale.domain.po.MusicQualitiesPO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
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
    @Mapping(source = "res.al", target = "album")
    @Mapping(source = "res.ar", target = "authors")
    abstract void detailAutoMapping(MusicDetailResult res, @MappingTarget MusicDTO model);

    public MusicDTO detailToModel(MusicDetailResult res) {
        return detailCore(res, new MusicDTO());
    }
    public MusicDTO detailToModel(MusicDetailResult res, MusicDTO model) {
        return detailCore(res, model);
    }

    public abstract void poToModel(MusicPO po, @MappingTarget MusicDTO model);

    @Mapping(source = "po.id", target = "album.id")
    public abstract void albumToModel(AlbumPO po, @MappingTarget MusicDTO model);

    private MusicDTO detailCore(MusicDetailResult res, MusicDTO model) {

        model.setNeteaseId(res.getId());
        model.setQualities(detailQualities(res));
        model.setCoverType(res.getOriginCoverType());
        model.setMvId(Long.parseLong(res.getMv()));
        model.setInfo(detailExtendInfo(res));

        detailAutoMapping(res, model);

        return model;
    }



    // 音乐扩展信息
    private MusicInfoExtendPO detailExtendInfo(MusicDetailResult res) {
        return MusicInfoExtendPO.builder()
                .no(res.getNo())
                .publishTime(res.getPublishTime())
                .build();
    }

    // 音质转换
    private List<MusicQualitiesPO> detailQualities(MusicDetailResult res) {
        List<MusicQualitiesPO> list = new ArrayList<>();

        // 低品质
        MusicQualitiesPO l = musicQualitiesDTO2POMapping.toPO(res.getL());
        if (l != null) {
            l.setId(res.getId());
            list.add(l);
        }

        // 中品质
        MusicQualitiesPO m = musicQualitiesDTO2POMapping.toPO(res.getM());
        if (m != null) {
            m.setId(res.getId());
            list.add(m);
        }

        // 高品质
        MusicQualitiesPO h = musicQualitiesDTO2POMapping.toPO(res.getH());
        if (h != null) {
            h.setId(res.getId());
            list.add(h);
        }

        // 无损
        MusicQualitiesPO sq = musicQualitiesDTO2POMapping.toPO(res.getSq());
        if (sq != null) {
            sq.setId(res.getId());
            list.add(sq);
        }

        // Hires
        MusicQualitiesPO hr = musicQualitiesDTO2POMapping.toPO(res.getHr());
        if (hr != null) {
            hr.setId(res.getId());
            list.add(hr);
        }

        return list;
    }

    /*
    ChorusDTO转MusicModel
    */

    public MusicDTO chorusToModel(ChorusResult res) {
        return chorusCore(res, new MusicDTO());
    }
    public MusicDTO chorusToModel(ChorusResult res, MusicDTO model) {
        return chorusCore(res, model);
    }

    private MusicDTO chorusCore(ChorusResult res, MusicDTO model) {

        model.setChorus(List.of(
                res.getStartTime(),
                res.getEndTime()
        ).toString());

        return model;
    }
}

