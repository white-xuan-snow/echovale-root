package com.echovale.music.appliaction.dto;

import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/23 11:42
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicAuthorsDTO {
    private Map<Long, List<Long>> musicLongIdToAuthorLongIdsMap;
    private Map<MusicId, List<AuthorId>> musicIdToAuthorIdsMap;
    private List<MusicId> musicIdList;
    private List<AuthorId> authorIdList;
    private List<Long> authorLongIds;
}
