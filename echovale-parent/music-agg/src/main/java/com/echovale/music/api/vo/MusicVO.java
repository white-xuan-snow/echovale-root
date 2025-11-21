package com.echovale.music.api.vo;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 11:49
 */

@Data
@Builder
@AllArgsConstructor
public class MusicVO {

    // 音乐基本信息
    MusicId id;
    NeteaseId neteaseId;
    String name;
    Integer fee;
    Integer coverType;
    Long mvId;
    Integer time;


    // 专辑基本信息
    AlbumVO album;

    // 歌手基本信息
    List<AuthorVO> authors;


    // 音乐音质信息
    List<MusicQualityVO> qualities;

    // 音乐副歌信息
    String chorus;

}
