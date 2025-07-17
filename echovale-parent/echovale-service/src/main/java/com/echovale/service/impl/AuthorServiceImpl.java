package com.echovale.service.impl;

import com.echovale.domain.mapper.AuthorMapper;
import com.echovale.domain.mapper.MusicAuthorsMapper;
import com.echovale.domain.po.AuthorPO;
import com.echovale.domain.po.MusicAuthorsPO;
import com.echovale.service.AuthorService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.netease.music.api.autoconfigure.configuration.api.AuthorApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/20 20:32
 */
@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorMapper authorMapper;
    @Autowired
    MusicAuthorsMapper musicAuthorsMapper;
    @Autowired
    AuthorApi authorApi;


    @Override
    public List<Long> nonentityNeteaseIds(List<Long> neteaseAuthorIds) {
        // 查询存在的id
        List<Long> existIds = authorMapper.selectObjs(new MPJLambdaWrapper<AuthorPO>()
                .select(AuthorPO::getNeteaseId)
                .in(AuthorPO::getNeteaseId, neteaseAuthorIds)
        );
        // 映射为HashSet
        HashSet<Long> existIdsSet = new HashSet<>(existIds);
        return neteaseAuthorIds.stream()
                .filter(o -> !existIdsSet.contains(o)) // 使用filter过滤已存在的id
                .toList();
    }

    @Async("ServiceNoneCore")
    @Override
    public CompletableFuture<AuthorDetailResult> elicitDetailsAsync(Long nonentityNeteaseAuthorId) {
        return CompletableFuture.supplyAsync(() -> {
           try {
               return authorApi.authorDetail(nonentityNeteaseAuthorId.toString());
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
        });
    }

    @Override
    @Transactional
    public void insertAuthors(List<AuthorPO> authorPOList) {
        authorMapper.insertOrUpdate(authorPOList);
    }

    @Override
    @Transactional
    public void insertMusicAuthors(List<MusicAuthorsPO> musicAuthorsPOList) {
        musicAuthorsMapper.insert(musicAuthorsPOList);
    }

    @Override
    public AuthorPO elicitAuthorDesc(Long id, String neteaseId) {

        if (id == null) {

        }


        return null;
    }
}
