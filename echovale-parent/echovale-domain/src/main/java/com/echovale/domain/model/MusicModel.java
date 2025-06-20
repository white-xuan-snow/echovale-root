package com.echovale.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.echovale.domain.po.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/11 0:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicModel {
    private Long id;
    private Long neteaseId;
    private String name;
    private Integer fee;
    private Integer coverType;
    private Long mvId;
    private Integer time;
    private String chorus;
    @TableField(exist = false)
    private AlbumPO album;
    @TableField(exist = false)
    private List<AuthorPO> authors;
    @TableField(exist = false)
    private List<MusicAwardsPO> awards;
    @TableField(exist = false)
    private LyricPO lyric;
    @TableField(exist = false)
    private MusicInfoExtendPO info;
    @TableField(exist = false)
    private List<MusicQualitiesPO> qualities;
    @TableField(exist = false)
    private List<MusicEntertainmentPO> entertainments;
    @TableField(exist = false)
    private List<LanguagePO> languagePOS;
    @TableField(exist = false)
    private List<MusicStylesPO> styles;
    @TableField(exist = false)
    private List<MusicTagsPO> tags;
    @TableField(exist = false)
    private List<MusicSheetsPO> sheets;
}
