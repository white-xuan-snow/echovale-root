package com.echovale.music.infrastructure.converter.qualifier;

import com.echovale.common.domain.api.constant.Common;
import com.echovale.music.api.vo.LyricVO;
import com.echovale.music.domain.entity.MusicQuality;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.MusicQualityConverter;
import com.echovale.shared.domain.valueobject.ActivityStatus;
import com.netease.music.api.autoconfigure.configuration.module.MusicModule;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.echovale.common.domain.api.constant.Common.LONG_EMPTY;

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
        return NeteaseId.byLong(id);
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

    public Long mapAuthorId(AuthorId id) {
        if (id == null) {
            return null;
        }
        return id.getId();
    }

    @Named("mapAuthorId")
    public AuthorId mapAuthorId(Long id) {
        return AuthorId.of(id);
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


    @Named("mapStatus")
    public Integer mapStatus(ActivityStatus status) {
        return status.getLevel();
    }

    @Named("mapStatus")
    public ActivityStatus mapStatus(Integer status) {
        return ActivityStatus.of(status);
    }



    // 外部数据转换为聚合根时，不需要映射Netease Id
    public AlbumId mapAlbumId(MusicDetailResult res) {
        return new AlbumId();
    }


    // Author


    public String mapAlias(List<String> res) {
        return res.toString();
    }



    // Album

    public LocalDateTime mapTime(Long time) {
        if (time == null || time.equals(LONG_EMPTY)) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }



    // MusicQuality

    public Integer mapLevel(String level) {
        return MusicQuality.QualityLevel.of(level).getCode();
    }


    public String mapLevel(Integer level) {
        return MusicQuality.QualityLevel.of(level).getDesc();
    }



    // MusicLyric


    @AfterMapping
    public void deleteTargetField(@MappingTarget LyricVO vo, @Context List<String> types) {
        for (Field field : vo.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            if (!types.contains(fieldName)) {
                try {
                    field.setAccessible(true);
                    field.set(vo, null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
