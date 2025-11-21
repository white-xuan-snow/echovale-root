package com.echovale.music.domain.service;


import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorSupplyService {
    List<Author> getAuthorList(MusicId id, NeteaseId neteaseId, List<AuthorId> authorIds) throws Exception;
}
