package com.echovale.music.appliaction.service.impl;

import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.appliaction.service.AuthorApplicationService;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.shared.utils.ListUtils;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/17 14:54
 */

@Slf4j
@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {


    @Autowired
    private AuthorQueryService authorQueryService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorConverter authorConverter;



    @Override
    public List<Author> saveAndQueryAuthorsByExternalDetailResult(List<AuthorDetailResult> distinctAuthorDetailResults) {

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


        return ListUtils.concat(existentAuthors, savedAuthors);
    }

    @Override
    public List<Author> fetchAuthorsByAuthorIds(List<AuthorId> authorIds) {
        if (CollectionUtils.isEmpty(authorIds))  {
            return Collections.emptyList();
        }

        return authorQueryService.queryAuthorsByIds(authorIds);
    }


}
