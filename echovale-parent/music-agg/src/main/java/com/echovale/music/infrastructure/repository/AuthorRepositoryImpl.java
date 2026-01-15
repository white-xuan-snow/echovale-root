package com.echovale.music.infrastructure.repository;

import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.repository.AuthorRepository;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.mapper.AuthorMapper;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.echovale.music.infrastructure.query.wrapper.AuthorWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 15:29
 */

@Slf4j
@Service
public class AuthorRepositoryImpl implements AuthorRepository {

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorWrapper authorWrapper;

    @Autowired
    private AuthorConverter authorConverter;

    @Override
    public Author findOrCreateByNeteaseId(Author author) {

        Long neteaseId = author.getNeteaseIdValue();

        MPJLambdaWrapper<AuthorPO> wrapper = authorWrapper.queryByNeteaseIdWrapper(neteaseId);

        AuthorPO authorPO = authorConverter.byAggregate(author);

        authorMapper.insertOrUpdate(authorPO);

        authorPO = authorMapper.selectOne(wrapper);

        return authorConverter.byPO(authorPO);
    }

    @Override
    public List<Author> save(List<Author> authors) {

        if (authors.isEmpty()) {
            return List.of();
        }

        List<AuthorPO> authorPOList = authors.stream()
                .map(authorConverter::byAggregate)
                .toList();

        authorMapper.insertOrUpdate(authorPOList);

        return authorPOList.stream()
                .map(authorConverter::byPO)
                .toList();
    }
}
