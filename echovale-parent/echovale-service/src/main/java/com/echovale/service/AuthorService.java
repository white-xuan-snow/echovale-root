package com.echovale.service;

import com.echovale.domain.po.AuthorPO;
import com.echovale.domain.po.MusicAuthorsPO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface AuthorService {
    List<Long> nonentityNeteaseIds(List<Long> neteaseAuthorIds);


    @Async("ServiceNoneCore")
    CompletableFuture<AuthorDetailResult> elicitDetails(Long nonentityNeteaseAuthorId);

    void insertAuthors(List<AuthorPO> authorPOList);

    void insertMusicAuthors(List<MusicAuthorsPO> musicAuthorsPOList);
}
