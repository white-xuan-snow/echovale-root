package com.echovale.playlist.infrastructure.query;

import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.query.MusicQueryService;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.application.dto.PlaylistDTO;
import com.echovale.playlist.application.query.PlaylistQueryService;
import com.echovale.playlist.domain.aggregate.Playlist;
import com.echovale.playlist.domain.valueobject.PlaylistId;
import com.echovale.playlist.infrastructure.converter.PlaylistConverter;
import com.echovale.playlist.infrastructure.mapper.PlaylistMapper;
import com.echovale.playlist.infrastructure.mapper.PlaylistMusicsMapper;
import com.echovale.playlist.infrastructure.po.PlaylistMusicsPO;
import com.echovale.playlist.infrastructure.po.PlaylistPO;
import com.echovale.playlist.infrastructure.query.wrapper.PlaylistWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/2 08:13
 */
@Slf4j
@Service
public class PlaylistQueryServiceImpl implements PlaylistQueryService {

    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private PlaylistWrapper playlistWrapper;

    @Autowired
    private PlaylistConverter playlistConverter;

    @Autowired
    private PlaylistMusicsMapper playlistMusicsMapper;

    @Autowired
    private MusicQueryService musicQueryService;

    @Override
    public Playlist queryPlaylist(PlaylistId playlistId) {
        // TODO

        // 查询歌单信息
        MPJLambdaWrapper<PlaylistPO> wrapper = playlistWrapper.queryPlaylistById(playlistId);
        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapper);

        return playlistConverter.byPO(playlistPO);
    }

    @Override
    public PlaylistId queryId(NeteaseId neteaseId) {

        MPJLambdaWrapper<PlaylistPO> wrapper = playlistWrapper.queryIdByNeteaseId(neteaseId);

        PlaylistPO playlistPO = playlistMapper.selectJoinOne(wrapper);

        if (playlistPO == null) {
            return new PlaylistId();
        }

        return PlaylistId.of(playlistPO.getId());
    }

    @Override
    public List<MusicId> queryMusicIds(PlaylistId playlistId, Integer page, Integer size) {

        MPJLambdaWrapper<PlaylistMusicsPO> wrapper = playlistWrapper.queryPlaylistMusicsById(playlistId);

        PageHelper.startPage(page, size);

        List<PlaylistMusicsPO> playlistMusicsPOList = playlistMusicsMapper.selectJoinList(wrapper);

        PageInfo<PlaylistMusicsPO> pageInfo = new PageInfo<>(playlistMusicsPOList);

        return pageInfo.getList().stream()
                .map(playlistConverter::byJoinPO)
                .toList();
    }
}
