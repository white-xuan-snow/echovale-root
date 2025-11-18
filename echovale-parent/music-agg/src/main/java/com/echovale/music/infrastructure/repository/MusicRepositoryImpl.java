package com.echovale.music.infrastructure.repository;

import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.query.MusicQueryServiceImpl;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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


    @Override
    public Music findByNeteaseId(NeteaseId id) {
        Long neteaseId = id.getId();

        return null;
    }

    @Override
    public Music save(MusicDetailResult musicDetailResult) {



        return null;
    }
}
