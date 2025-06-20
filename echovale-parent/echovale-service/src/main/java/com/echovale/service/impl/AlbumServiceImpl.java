package com.echovale.service.impl;

import com.echovale.domain.mapper.AlbumMapper;
import com.echovale.domain.po.AlbumPO;
import com.echovale.service.AlbumService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.netease.music.api.autoconfigure.configuration.api.AlbumApi;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.AlbumListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/20 20:31
 */
@Slf4j
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    AlbumApi albumApi;


    @Override
    public List<Long> nonentityNeteaseIds(List<Long> neteaseAuthorIds) {
        List<Long> existIds = albumMapper.selectObjs(new MPJLambdaWrapper<>(AlbumPO.class)
                .select(AlbumPO::getNeteaseId)
                .in(AlbumPO::getNeteaseId, neteaseAuthorIds)
        );
        HashSet<Long> existIdsSet = new HashSet<>(existIds);
        return neteaseAuthorIds.stream()
                .filter(existIdsSet::contains)
                .toList();
    }

    @Async("ServiceNoneCore")
    @Override
    public CompletableFuture<AlbumListDTO> elicitAlbum(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return albumApi.album(id.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
