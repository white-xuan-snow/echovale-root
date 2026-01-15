package com.echovale.music.infrastructure.repository;

import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import com.echovale.music.domain.entity.Lyric;
import com.echovale.music.domain.repository.MusicRepository;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.converter.MusicConverter;
import com.echovale.music.infrastructure.converter.MusicLyricConverter;
import com.echovale.music.infrastructure.converter.MusicQualityConverter;
import com.echovale.music.infrastructure.mapper.AuthorMapper;
import com.echovale.music.infrastructure.mapper.LyricMapper;
import com.echovale.music.infrastructure.mapper.MusicMapper;
import com.echovale.music.infrastructure.mapper.MusicQualitiesMapper;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.echovale.music.infrastructure.po.LyricPO;
import com.echovale.music.infrastructure.po.MusicPO;
import com.echovale.music.infrastructure.po.MusicQualitiesPO;
import com.echovale.music.infrastructure.query.MusicQueryServiceImpl;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    private MusicQualitiesMapper musicQualitiesMapper;


    @Autowired
    private MusicConverter musicConverter;

    @Autowired
    private MusicQualityConverter musicQualityConverter;

    @Autowired
    private MusicLyricConverter musicLyricConverter;

    @Autowired
    private LyricMapper lyricMapper;

    @Override
    public Music findByNeteaseId(NeteaseId id) {
        Long neteaseId = id.getId();

        return null;
    }

    @Override
    public Music save(Music music) {

        MusicPO musicPO = musicConverter.toPO(music);

        musicMapper.insertOrUpdate(musicPO);

        MusicId musicId = MusicId.of(musicPO.getId());

        List<MusicQualitiesPO> musicQualitiesPOList = music.getQualities().stream()
                .map(o -> musicQualityConverter.toPO(o, musicId))
                .toList();

        musicQualitiesMapper.insertOrUpdate(musicQualitiesPOList);

        return musicConverter.toAggregateByPOS(musicPO, musicQualitiesPOList);
    }

    @Override
    public List<Author> saveAuthors(List<Author> authors) {
        List<AuthorPO> authorPOS = authors.stream()
                .map(authorConverter::byAggregate)
                .toList();

        authorMapper.insertOrUpdate(authorPOS);

        return null;
    }

    @Override
    public Lyric saveLyric(Lyric lyric) {

        LyricPO lyricPO = musicLyricConverter.toPO(lyric);

        lyricMapper.insertOrUpdate(lyricPO);

        return musicLyricConverter.toAggregate(lyricPO);
    }

    @Override
    public List<Music> saveAll(List<Music> musics) {
        List<MusicPO> musicPOS = musicConverter.toPOS(musics);

        musicMapper.insertOrUpdate(musicPOS);

        List<Music> result = new ArrayList<>(musicPOS.size());
        List<MusicQualitiesPO> allMusicQualitiesPOList = new ArrayList<>();

        for (int i = 0; i < musicPOS.size(); i++) {
            MusicPO musicPO = musicPOS.get(i);
            Music music = musics.get(i);
            MusicId musicId = MusicId.of(musicPO.getId());

            List<MusicQualitiesPO> musicQualitiesPOList = music.getQualities().stream()
                    .map(o -> musicQualityConverter.toPO(o, musicId))
                    .toList();

            allMusicQualitiesPOList.addAll(musicQualitiesPOList);


            result.add(musicConverter.toAggregateByPOS(musicPO, musicQualitiesPOList));
        }

        if (!allMusicQualitiesPOList.isEmpty()) {
            musicQualitiesMapper.insertOrUpdate(allMusicQualitiesPOList);
        }


        return result;
    }
}
