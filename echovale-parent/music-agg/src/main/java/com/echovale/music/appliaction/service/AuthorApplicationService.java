package com.echovale.music.appliaction.service;


import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorApplicationService {


    List<Author> saveAndQueryAuthorsByExternalDetailResult(List<AuthorDetailResult> distinctAuthorDetailResults);


    List<Author> fetchAuthorsByAuthorIds(List<AuthorId> authorIds);
}
