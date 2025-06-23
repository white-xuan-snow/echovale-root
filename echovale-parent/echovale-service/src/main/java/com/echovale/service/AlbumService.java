package com.echovale.service;

import com.echovale.domain.po.AlbumPO;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.AlbumListDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface AlbumService {
    List<Long> nonentityNeteaseIds(List<Long> neteaseAuthorIds);

    @Async("ServiceNoneCore")
    CompletableFuture<AlbumListDTO> elicitAlbum(Long id);

    void insertAlbums(List<AlbumPO> albumPOList);
}
