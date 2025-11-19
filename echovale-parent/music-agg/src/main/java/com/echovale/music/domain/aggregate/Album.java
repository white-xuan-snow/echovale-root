package com.echovale.music.domain.aggregate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.NeteaseId;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 21:24
 */

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Album {
    private AlbumId id;
    private String name;
    private NeteaseId neteaseId;
    private String description;
    private String picUrl;
    private LocalDateTime publishTime;
    private String type;
    private Integer size;
    private String subType;
    private List<AuthorId> authorIds;
}
