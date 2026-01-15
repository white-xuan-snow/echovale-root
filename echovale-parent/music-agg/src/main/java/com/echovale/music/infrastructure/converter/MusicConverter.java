package com.echovale.music.infrastructure.converter;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.common.domain.infrastructure.config.MappingConfig;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.qualifier.MusicQualifier;
import com.echovale.music.infrastructure.po.MusicPO;
import com.echovale.music.infrastructure.po.MusicQualitiesPO;
import com.echovale.shared.domain.valueobject.ActivityStatus;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.mapstruct.*;

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
                MusicId.class,
                NeteaseId.class,
                AlbumId.class,
                List.class,
                ActivityStatus.class
        },
        uses = {
                AlbumConverter.class,
                AuthorConverter.class,
                MusicQualityConverter.class,
                MusicQualifier.class
        }
)
public interface MusicConverter {


    //    @Mapping(target = "id", expression = "java(MusicId.of(res.getId()))")
//    @Mapping(target = "neteaseId", expression = "java(new NeteaseId(res.getNeteaseId()))")
//    @Mapping(target = "albumId", expression = "java(new AlbumId(res.getAlbumId()))")
    @Mapping(target = "id", source = "res.id")
    @Mapping(target = "neteaseId", source = "res.neteaseId", qualifiedByName = "mapNeteaseId")
    @Mapping(target = "albumId", source = "res.albumId", qualifiedByName = "mapAlbumId")
    @Mapping(target = "qualities", source = "q")
    @Mapping(target = "status", source = "res.status", qualifiedByName = "mapStatus")
    Music toAggregateByPOS(MusicPO res, List<MusicQualitiesPO> q);





//    public Music toAggregate(MusicDetailResult musicDetailResult) {

//
//        return music;
//    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neteaseId", source = "res.id", qualifiedByName = "mapNeteaseId")
    @Mapping(target = "mvId", source = "res.mv")
    @Mapping(target = "coverType", source = "originCoverType")
    @Mapping(target = "qualities", source = "res", qualifiedByName = "mapMusicQualities")
    Music byDetailResult(MusicDetailResult res);


    @InheritConfiguration(name = "byDetailResult")
    @Mapping(target = "status", expression = "java(ActivityStatus.HALF_FILLED)")
    Music byInsufficientDetailResult(MusicDetailResult res);


    @InheritConfiguration(name = "byDetailResult")
    @Mapping(target = "status", expression = "java(ActivityStatus.FULL)")
    Music bySufficientDetailResult(MusicDetailResult res);



    MusicVO toVO(Music res, @MappingTarget MusicVO target);


    @Mapping(target = "authors", source = "res.authorList")
    @Mapping(target = "album", source = "res.album")
    @Mapping(target = "id", source = "res.music.id")
    @Mapping(target = "neteaseId", source = "res.music.neteaseId")
    @Mapping(target = "name", source = "res.music.name")
//    @Mapping(target = "qualities", source = "res.qualities")
    @Mapping(target = ".", source = "res.music")
    MusicVO toVO(MusicDTO res);


    @Mapping(target = "authors", source = "authors")
    @Mapping(target = "album", source = "album")
    @Mapping(target = "id", source = "music.id")
    @Mapping(target = "neteaseId", source = "music.neteaseId")
    @Mapping(target = "name", source = "music.name")
    @Mapping(target = ".", source = "music")
    MusicVO toVO(Music music, List<Author> authors, Album album);


//    public MusicDTO toAggregate(Music music, List<Author> authors, Album album) {
//        return MusicDTO.builder()
//                .music(music)
//                .authorList(authors)
//                .album(album)
//                .build();
//    }


    @Mapping(target = "id", source = "res.id")
    @Mapping(target = "neteaseId", source = "res.neteaseId")
    @Mapping(target = "albumId", source = "res.albumId")
    @Mapping(target = "status", source = "res.status", qualifiedByName = "mapStatus")
    MusicPO toPO(Music res);




    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chorus", source = "res")
    Music addChorus(ChorusResult res, @MappingTarget Music music);


    /**
     * 将Music实体及其相关的作者和专辑信息转换为MusicDTO对象
     *
     * @param res     Music实体对象，包含音乐的基本信息
     * @param authors 作者列表，与音乐相关联的作者信息
     * @param album   专辑对象，包含音乐所属专辑的信息
     * @return 转换后的MusicDTO对象，用于数据传输
     */
    @Mapping(target = "music", source = "res")
    @Mapping(target = "authorList", source = "authors")
    @Mapping(target = "album", source = "album")
    MusicDTO toDTO(Music res, List<Author> authors, Album album);


    List<MusicPO> toPOS(List<Music> musics);
}
