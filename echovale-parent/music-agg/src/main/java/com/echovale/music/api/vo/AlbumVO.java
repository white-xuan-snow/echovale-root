package com.echovale.music.api.vo;

import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 21:09
 */

@Data
@Builder
@AllArgsConstructor
public class AlbumVO {
    AlbumId id;
    String name;
    NeteaseId neteaseId;
    String description;
    String picUrl;
    LocalDateTime publishTime;
    String type;
    Integer size;
    String subType;
    List<AuthorVO> authors;
}
