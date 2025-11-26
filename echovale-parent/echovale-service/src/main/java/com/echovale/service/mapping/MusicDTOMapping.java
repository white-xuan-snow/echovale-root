package com.echovale.service.mapping;

import com.echovale.common.utils.StringUtils;
import com.echovale.common.utils.TimeUtils;
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
        uses = {MusicQualitiesPOMapping.class,
                AuthorPOMapping.class,
                AlbumPOMapping.class,})
public abstract class MusicDTOMapping {

    @Autowired
    MusicQualitiesPOMapping musicQualitiesPOMapping;
    @Autowired
    TimeUtils timeUtils;


    /*
    MusicDetailDTO转MusicModel
    */
    // 自动匹配映射
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "res.al", target = "album")
    @Mapping(source = "res.ar", target = "authors")
    abstract void detailAutoMapping(MusicDetailResult res, @MappingTarget MusicDTO model);

    public MusicDTO byDetailResult(MusicDetailResult res) {
        return detailCore(res, new MusicDTO());
    }
    public MusicDTO byDetailResult(MusicDetailResult res, MusicDTO model) {
        return detailCore(res, model);
    }

    public abstract void byPO(MusicPO po, @MappingTarget MusicDTO model);

    @Mapping(target = "neteaseId", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", ignore = true)
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
                .publishTime(timeUtils.long2LocalDateTime(res.getPublishTime()))
                .build();
    }

    // 音质转换
    private List<MusicQualitiesPO> detailQualities(MusicDetailResult res) {
        List<MusicQualitiesPO> list = new ArrayList<>();

        // 低品质
        MusicQualitiesPO l = musicQualitiesPOMapping.byResult(res.getL());
        if (l != null) {
            l.setId(res.getId());
            list.add(l);
        }

        // 中品质
        MusicQualitiesPO m = musicQualitiesPOMapping.byResult(res.getM());
        if (m != null) {
            m.setId(res.getId());
            list.add(m);
        }

        // 高品质
        MusicQualitiesPO h = musicQualitiesPOMapping.byResult(res.getH());
        if (h != null) {
            h.setId(res.getId());
            list.add(h);
        }

        // 无损
        MusicQualitiesPO sq = musicQualitiesPOMapping.byResult(res.getSq());
        if (sq != null) {
            sq.setId(res.getId());
            list.add(sq);
        }

        // Hires
        MusicQualitiesPO hr = musicQualitiesPOMapping.byResult(res.getHr());
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

    @Autowired
    StringUtils stringUtils;

    private MusicDTO chorusCore(ChorusResult res, MusicDTO model) {
        // 如果没有副歌，跳过赋值

        Integer startTime = res == null ? 0 : res.getStartTime();
        Integer endTime = res == null ? 0 : res.getEndTime();

        model.setChorus(stringUtils.list2String(List.of(startTime, endTime)));

        return model;
    }
}

