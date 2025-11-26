package com.echovale.music.infrastructure.converter.qualifier;

import com.echovale.music.domain.entity.MusicQuality;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.MusicQualityConverter;
import com.netease.music.api.autoconfigure.configuration.module.MusicModule;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.mapstruct.Named;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/26 15:11
 */

@Component
public class MusicQualifier {

    private MusicQualityConverter musicQualityConverter;

    public MusicQualifier(@Lazy MusicQualityConverter musicQualityConverter) {
        this.musicQualityConverter = musicQualityConverter;
    }

    @Named("mapMusicQualities")
    public List<MusicQuality> getMusicQualityConverter(MusicDetailResult res) {

        List<MusicQuality> musicQualities = new ArrayList<>();
        musicQualities.add(musicQualityConverter.toAggregate(res.getL(), MusicModule.Level.STANDARD, res.getId()));
        musicQualities.add(musicQualityConverter.toAggregate(res.getH(), MusicModule.Level.HIGHER, res.getId()));
        musicQualities.add(musicQualityConverter.toAggregate(res.getM(), MusicModule.Level.EX_HIGH, res.getId()));
        musicQualities.add(musicQualityConverter.toAggregate(res.getSq(), MusicModule.Level.LOSSLESS, res.getId()));
        musicQualities.add(musicQualityConverter.toAggregate(res.getHr(), MusicModule.Level.HIRES, res.getId()));

        return musicQualities;
    }


    public MusicId mapMusicid(Long id) {
        return MusicId.of(id);
    }

    @Named("mapNeteaseId")
    public NeteaseId mapNeteaseId(Long id) {
        return NeteaseId.of(id);
    }

    @Named("mapAlbumId")
    public AlbumId mapAlbumId(Long id) {
        return AlbumId.of(id);
    }

    public Long mapMusicId(MusicId id) {
        if (id == null) {
            return null;
        }
        return id.getId();
    }

    public Long mapNeteaseId(NeteaseId id) {
        if (id == null) {
            return null;
        }
        return id.getId();
    }


    public Long mapAlbumId(AlbumId id) {
        if (id == null) {
            return null;
        }
        return id.getId();
    }

    public Long mapMvId(String mvId) {
        if (mvId == null) {
            return null;
        }
        return Long.parseLong(mvId);
    }

    public String mapChorus(ChorusResult chorus) {
        return List.of(chorus.getStartTime(), chorus.getEndTime()).toString();
    }


    // 外部数据转换为聚合根时，不需要映射Netease Id
    public AlbumId mapAlbumId(MusicDetailResult res) {
        return new AlbumId();
    }














    // MusicQuality

    public Integer mapLevel(String level) {
        return MusicQuality.QualityLevel.of(level).getCode();
    }


    public String mapLevel(Integer level) {
        return MusicQuality.QualityLevel.of(level).getDesc();
    }





}
