package com.echovale.music.api.vo;

import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.NeteaseId;
import lombok.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 21:07
 */

@Data
@Builder
@AllArgsConstructor
public class AuthorVO {
    AuthorId id;
    String name;
    NeteaseId neteaseId;
    String transName;
    String alias;
    Integer musicSize;
    Integer albumSize;
    Integer mvSize;
    String coverUrl;
    String avatarUrl;
    String description;
    String identify;
}
