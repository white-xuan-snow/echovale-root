package com.echovale.music.domain.service;

import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.gateway.MusicApiGateway;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Author> getAuthorList(MusicId id, NeteaseId neteaseId, List<AuthorId> authorIds) throws Exception {

        List<Author> authorList;

        if (authorIds != null && !authorIds.isEmpty()) {

            // 从数据库查询
            authorList = authorQueryService.queryAuthorsByIds(authorIds);

        } else {

            // 从外部获取数据
            if (!neteaseId.isValid()) {

                // 获取外部数据ID
                neteaseId = musicQueryService.queryNeteaseIdById(id);

            }

            List<AuthorDetailResult> authorDetailResults = musicApiGateway.elicitMusicAuthors(neteaseId);

            authorList = authorDetailResults.stream()
                    .map(authorConverter::byDetailResult)
                    .toList();

        }

        return authorList;
    }
}
