package com.echovale.shared.external.gateway.impl;

import com.echovale.common.domain.api.aspect.MethodsInvokingTime;
import com.echovale.common.domain.api.exception.BadGatewayInvokingException;
import com.echovale.shared.external.gateway.MusicNeteaseApiGateway;
import com.netease.music.api.autoconfigure.configuration.api.AlbumApi;
import com.netease.music.api.autoconfigure.configuration.api.AuthorApi;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 10:57
 */
@Slf4j
@Service
public class MusicNeteaseApiGatewayImpl implements MusicNeteaseApiGateway {

    @Autowired
    private MusicApi musicApi;

    @Autowired
    private AlbumApi albumApi;

    @Autowired
    private AuthorApi authorApi;


    @Override
    public List<MusicDetailResult> getMusicDetail(List<Long> musicIds) {
        try {
            return musicApi.getMusicDetail(musicIds);
        } catch (Exception e) {
            log.error("[共享].[网关] 调用音乐详情接口失败", e);
            throw new BadGatewayInvokingException(e.getMessage());
        }
    }

    @Override
    public AlbumListResult album(Long id) {
        try {
            return albumApi.album(id);
        } catch (Exception e) {
            log.error("[共享].[网关] 调用专辑详情接口失败", e);
            throw new BadGatewayInvokingException(e.getMessage());
        }
    }

    @Override
    public List<ChorusResult> getChorus(List<Long> neteaseIdList) {
        try {
            return musicApi.getChorus(neteaseIdList);
        } catch (Exception e) {
            log.error("[共享].[网关] 调用歌词接口失败", e);
            throw new BadGatewayInvokingException(e.getMessage());
        }
    }

    @Override
    public LyricsResult getLyrics(Long id) {
        try {
            return musicApi.getLyrics(id);
        } catch (Exception e) {
            log.error("[共享].[网关] 调用歌词接口失败", e);
            throw new BadGatewayInvokingException(e.getMessage());
        }
    }


    @Override
    public List<MusicUrlResult> getMusicV1Url(List<Long> apiIdList, String level) {
        try {
            return musicApi.getMusicV1Url(apiIdList, level);
        } catch (Exception e) {
            log.error("[共享].[网关] 调用音乐接口失败", e);
            throw new BadGatewayInvokingException(e.getMessage());
        }
    }

    @Override
    public PlaylistResult getPlaylist(Long neteaseId) {
        try {
            return musicApi.playlist(neteaseId);
        } catch (Exception e) {
            log.error("[共享].[网关] 调用歌单接口失败", e);
            throw new BadGatewayInvokingException(e.getMessage());
        }
    }
}
