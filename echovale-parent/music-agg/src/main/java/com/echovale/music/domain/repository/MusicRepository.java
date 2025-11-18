package com.echovale.music.domain.repository;


import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository {
    Music findByNeteaseId(NeteaseId id);

    Music save(MusicDetailResult musicDetailResult);
}
