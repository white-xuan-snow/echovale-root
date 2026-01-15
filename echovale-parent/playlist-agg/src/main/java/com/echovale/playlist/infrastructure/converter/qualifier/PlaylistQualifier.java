package com.echovale.playlist.infrastructure.converter.qualifier;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.playlist.domain.valueobject.PlaylistId;
import com.echovale.playlist.infrastructure.po.PlaylistMusicsPO;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/3 14:47
 */

@Component
public class PlaylistQualifier {

    public Long mapPlaylistId(PlaylistId id) {
        if (id == null) {
            return null;
        }
        return id.getId();
    }

    @Named("mapPlaylistId")
    public PlaylistId mapPlaylistId(Long id) {
        return PlaylistId.of(id);
    }


    public List<String> mapTags(String tags) {
        if (tags == null) {
            return null;
        }
        return List.of(tags.split(","));
    }



    public MusicId mapMusicId(PlaylistMusicsPO res) {
        return MusicId.of(res.getMusicId());
    }

}
