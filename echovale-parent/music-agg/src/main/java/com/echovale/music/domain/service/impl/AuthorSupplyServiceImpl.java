package com.echovale.music.domain.service.impl;

import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.domain.service.AuthorSupplyService;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.shared.utils.ListUtil;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/20 14:37
 */


@Slf4j
@Service
public class AuthorSupplyServiceImpl implements AuthorSupplyService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorQueryService authorQueryService;

    @Autowired
    private AuthorConverter authorConverter;

    @Autowired
    private MusicQueryService musicQueryService;

    @Autowired
    private MusicApiGateway musicApiGateway;


    @Override
    public List<Author> getAuthorList(MusicId id, NeteaseId neteaseId, List<AuthorId> authorIds) {

        if (authorIds != null && !authorIds.isEmpty()) {
            // 从数据库查询
            return authorQueryService.queryAuthorsByIds(authorIds);
        }

        // 从外部获取数据
        if (!neteaseId.isValid()) {
            // 获取外部数据ID
            neteaseId = musicQueryService.queryNeteaseIdById(id);
        }

        // 没获取到
        if (!neteaseId.isValid()) {
            return List.of();
        }

        // 获取数据
        List<AuthorDetailResult> authorDetailResults = musicApiGateway.elicitMusicAuthors(neteaseId);

        return authorDetailResults.stream()
                .map(authorConverter::byDetailResult)
                .toList();
    }

    @Override
    public List<Author> createAndSaveAuthors(List<AuthorDetailResult> distinctAuthorDetailResults) {

        List<NeteaseId> authorNeteaseIds = NeteaseId.byAuthorsDetailResult(distinctAuthorDetailResults);

        // 通过AuthorIds查询未持久化的AuthorIds
        List<NeteaseId> nonexistentNeteaseIds = authorQueryService.queryNonexistentAuthorIds(authorNeteaseIds);

        List<Long> nonexistentIds = NeteaseId.getLongList(nonexistentNeteaseIds);
        HashSet<Long> nonexistentAuthorIdSet = new HashSet<>(nonexistentIds);

        List<Author> existentAuthors = distinctAuthorDetailResults.stream()
                .filter(author -> !nonexistentAuthorIdSet.contains(author.getId()))
                .map(authorConverter::byInsufficientDetailResult)
                .toList();

        List<AuthorDetailResult> persistentAuthorDetailResults = distinctAuthorDetailResults.stream()
                .filter(author -> nonexistentAuthorIdSet.contains(author.getId()))
                .toList();





        List<Author> persistentAuthors = persistentAuthorDetailResults.stream()
                .map(authorConverter::byInsufficientDetailResult)
                .toList();

        List<Author> savedAuthors = authorRepository.save(persistentAuthors);


        return ListUtil.concat(existentAuthors, savedAuthors);
    }
}
