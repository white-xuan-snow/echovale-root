package com.echovale.music.domain.aggregate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.echovale.music.domain.entity.MusicEntertainment;
import com.echovale.music.domain.entity.MusicQuality;
import com.echovale.music.domain.valueobject.*;
import com.echovale.music.infrastructure.po.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.mapstruct.ap.internal.util.accessor.Accessor;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 15:08
 */

@Getter
// access = AccessLevel.PRIVATE
// 限制外部使用Builder
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Music {
    private Long id;
    private Long neteaseId;
    private String name;
    private Integer fee;
    private Integer coverType;
    private Long mvId;
    private Integer time;
    private String chorus;


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



}
