package com.echovale.music.appliaction.model;

import com.echovale.shared.infrastructure.utils.ListUtil;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/26 14:28
 */

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExternalMusicMapHolder {
    Map<Long, List<Long>> musicIdToAuthorIds;
    Map<Long, Long> musicIdToAlbumId;


    public static ExternalMusicMapHolder from(List<MusicDetailResult> tracks) {
        if (CollectionUtils.isEmpty(tracks)) {
            return new ExternalMusicMapHolder(Collections.emptyMap(), Collections.emptyMap());
        }

        int capacity = ListUtil.getInitialCapacity(tracks.size());
        Map<Long, Long> albumMap = new HashMap<>(capacity);
        Map<Long, List<Long>> authorMap = new HashMap<>(capacity);

        for (MusicDetailResult track : tracks) {
            Long id = track.getId();

            if (track.getAl() != null) {
                albumMap.put(id, track.getAl().getId());
            }

            List<AuthorDetailResult> authors = track.getAr();
            if (authors != null) {
                List<Long> authorIds = new ArrayList<>(authors.size());
                for (AuthorDetailResult author : authors) {
                    authorIds.add(author.getId());
                }
                authorMap.put(id, authorIds);
            }
        }
        return new ExternalMusicMapHolder(
                Collections.unmodifiableMap(authorMap),
                Collections.unmodifiableMap(albumMap)
        );
    }
}
