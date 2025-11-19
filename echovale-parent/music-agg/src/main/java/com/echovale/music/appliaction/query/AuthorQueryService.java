package com.echovale.music.appliaction.query;


import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.MusicId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 */
@Service
public interface AuthorQueryService {
    List<Author> queryAuthorsByMusicId(MusicId id);
}
