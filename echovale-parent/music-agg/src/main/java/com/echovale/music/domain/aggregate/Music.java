package com.echovale.music.domain.aggregate;

import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.entity.MusicEntertainment;
import com.echovale.music.domain.entity.MusicQuality;
import com.echovale.music.domain.valueobject.*;
import com.echovale.shared.domain.valueobject.ActivityStatus;
import lombok.*;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:08
 */

@Data
// access = AccessLevel.PRIVATE
// 限制外部使用Builder
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Music {
    private MusicId id;
    private NeteaseId neteaseId;
    private String name;
    private Integer fee;
    private Integer coverType;
    private Long mvId;
    private Integer time;
    private String chorus;
    private ActivityStatus status;


    private Lyric lyric;
    private MusicInfoExtend info;
    private List<MusicQuality> qualities;
    private List<MusicEntertainment> entertainments;


    private AlbumId albumId;
    private List<AuthorId> authorIds;
    private List<MusicAwardsId> awardsIds;
    private List<MusicLanguageId> languageIds;
    private List<MusicStyleId> styleIds;
    private List<MusicTagId> tagIds;
    private List<MusicSheetId> sheetIds;





    public static Music createNew() {


        return Music.builder()
                .build();
    }



    public Long getMusicIdValue() {
        return id == null ? null : id.getId();
    }

    public Long getNeteaseIdValue() {
        return neteaseId == null ? null : neteaseId.getId();
    }


    public Long getAlbumIdValue() {
        return albumId == null ? null : albumId.getId();
    }



    public Boolean isNull() {
        return id == null;
    }





}
