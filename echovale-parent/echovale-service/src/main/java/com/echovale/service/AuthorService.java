package com.echovale.service;

import com.netease.music.api.autoconfigure.configuration.pojo.dto.AuthorDetailDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface AuthorService {
    List<Long> nonentityNeteaseIds(List<Long> neteaseAuthorIds);


    @Async("ServiceNoneCore")
    CompletableFuture<AuthorDetailDTO> elicitDetails(Long nonentityNeteaseAuthorId);
}
