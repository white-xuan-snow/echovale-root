package com.echovale.music.domain.repository;


import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository {
    Music findByNeteaseId(NeteaseId id);

    Music save(Music music);

    List<Author> saveAuthors(List<Author> authors);

    Lyric saveLyric(Lyric lyric);

    List<Music> saveAll(List<Music> musics);
}
