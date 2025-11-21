package com.echovale.music.infrastructure.repository;

import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.echovale.music.infrastructure.mapper.AuthorMapper;
import com.echovale.music.infrastructure.mapper.MusicMapper;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.echovale.music.infrastructure.po.MusicPO;
import com.echovale.music.infrastructure.query.MusicQueryServiceImpl;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 20:20
 */

@Repository
public class MusicRepositoryImpl implements MusicRepository {

    @Autowired
    private MusicQueryServiceImpl musicQueryServiceImpl;

    @Autowired
    private AuthorConverter authorConverter;


    @Autowired
    private AuthorMapper authorMapper;


    @Autowired
    private MusicMapper musicMapper;


    @Autowired
    private MusicConverter musicConverter;

    @Override
    public Music findByNeteaseId(NeteaseId id) {
        Long neteaseId = id.getId();

        return null;
    }

    @Override
    public Music save(Music music) {

        MusicPO musicPO = musicConverter.toPO(music);

        musicMapper.insertOrUpdate(musicPO);

        return musicConverter.toAggregate(musicPO);
    }

    @Override
    public List<Author> saveAuthors(List<Author> authors) {
        List<AuthorPO> authorPOS = authors.stream()
                .map(authorConverter::byAggregate)
                .toList();

        authorMapper.insertOrUpdate(authorPOS);

        return null;
    }
}
