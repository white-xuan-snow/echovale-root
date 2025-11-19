package com.echovale.music.domain.aggregate;

import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.NeteaseId;
import lombok.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 21:21
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private AuthorId id;
    private String name;
    private NeteaseId neteaseId;
    private String transName;
    private String alias;
    private Integer musicSize;
    private Integer albumSize;
    private Integer mvSize;
    private String coverUrl;
    private String avatarUrl;
    private String description;
    private String identify;


    public Long getNeteaseIdValue() {
        return neteaseId.getId();
    }


    public Long getIdValue() {
        return id.getId();
    }
}
