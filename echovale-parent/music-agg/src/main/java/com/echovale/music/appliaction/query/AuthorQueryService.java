package com.echovale.music.appliaction.query;


import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 30531
 */
@Service
public interface AuthorQueryService {
    List<Author> queryAuthorsByMusicId(MusicId id);

    List<Author> queryAuthorsByIds(List<AuthorId> authorIds);

    List<Author> queryAuthorsByAlbumId(AlbumId id);

    List<NeteaseId> queryNonexistentAuthorIds(List<NeteaseId> authorIds);

}
