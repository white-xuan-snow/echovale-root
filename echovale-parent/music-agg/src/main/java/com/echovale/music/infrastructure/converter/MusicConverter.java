package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.infrastructure.config.MappingConfig;
import com.echovale.music.infrastructure.po.MusicPO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 20:04
 */


@Mapper(config = MappingConfig.class,
        componentModel = "spring",
        imports = {
                com.echovale.music.domain.valueobject.MusicId.class,
                com.echovale.music.domain.valueobject.NeteaseId.class,
                com.echovale.music.domain.valueobject.AlbumId.class
        },
        uses = {
                AlbumConverter.class,
                AuthorConverter.class,
                java.util.List.class
        }
)
public abstract class MusicConverter {

    public Music toAggregate(MusicPO musicPO) {
        return autoMapping(musicPO);
    }

    @Mapping(target = "id", expression = "java(new MusicId(res.getId()))")
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getNeteaseId()))")
    @Mapping(target = "albumId", expression = "java(new AlbumId(res.getAlbumId()))")
    abstract Music autoMapping(MusicPO res);


    public Music toAggregate(MusicDetailResult musicDetailResult) {
        return autoMapping(musicDetailResult);
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getId()))")
    @Mapping(target = "albumId", expression = "java(new AlbumId(res.getAl().getId()))")
    @Mapping(target = "mvId", expression = "java(Long.parseLong(res.getMv()))")
    @Mapping(target = "coverType", source = "originCoverType")
    abstract Music autoMapping(MusicDetailResult res);



    public MusicVO byAggregate(Music music, MusicVO musicVO) {
        return core(music, musicVO);
    }

    public MusicVO byAggregate(Music music) {
        return core(music, MusicVO.builder().build());
    }

    abstract MusicVO autoMapping(Music res, @MappingTarget MusicVO target);


    private MusicVO core(Music music, MusicVO musicVO) {
        return autoMapping(music, musicVO);
    }


    public MusicVO byAggregates(Music music, List<Author> authors, Album album, MusicVO musicVO) {
        return core(music, authors, album, musicVO);
    }

    public MusicVO byAggregates(Music music, List<Author> authors, Album album) {
        return core(music, authors, album, MusicVO.builder().build());
    }


    @Mapping(target = "authors", source = "as")
    @Mapping(target = "album", source = "a")
    @Mapping(target = "id", source = "res.id")
    @Mapping(target = "neteaseId", source = "res.neteaseId")
    @Mapping(target = "name", source = "res.name")
    abstract MusicVO autoMapping(Music res, List<Author> as, Album a, @MappingTarget MusicVO target);


    private MusicVO core(Music music, List<Author> authors, Album album, MusicVO musicVO) {
        return autoMapping(music, authors, album, musicVO);
    }


    public MusicDTO toAggregate(Music music, List<Author> authors, Album album) {
        return MusicDTO.builder()
                .music(music)
                .authorList(authors)
                .album(album)
                .build();
    }



    public MusicVO byDTO(MusicDTO musicDTO) {
        return byAggregates(musicDTO.getMusic(), musicDTO.getAuthorList(), musicDTO.getAlbum());
    }




    @Mapping(target = "id", expression = "java(res.getMusicIdValue())")
    @Mapping(target = "neteaseId", expression = "java(res.getNeteaseIdValue())")
    @Mapping(target = "albumId", expression = "java(res.getAlbumIdValue())")
    abstract MusicPO autoMapping(Music res);



    public MusicPO toPO(Music music) {
        return autoMapping(music);
    }

    @Mapping(target = "chorus", expression = "java(List.of(res.getStartTime(), res.getEndTime()))")
    abstract Music autoMapping(ChorusResult res, @MappingTarget Music music);

    public Music addChorus(Music music, ChorusResult chorusResult) {
        return autoMapping(chorusResult, music);
    }
}
