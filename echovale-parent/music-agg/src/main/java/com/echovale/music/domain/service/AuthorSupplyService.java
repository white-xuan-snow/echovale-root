package com.echovale.music.domain.service;


import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorSupplyService {
    List<Author> getAuthorList(MusicId id, NeteaseId neteaseId, List<AuthorId> authorIds);

    List<Author> createAndSaveAuthors(List<AuthorDetailResult> distinctAuthorDetailResults);
}
