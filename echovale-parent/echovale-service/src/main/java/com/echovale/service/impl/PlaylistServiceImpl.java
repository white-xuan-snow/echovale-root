package com.echovale.service.impl;

import com.echovale.service.PlaylistService;
import com.echovale.service.vo.PlaylistVO;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.PlaylistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/12 15:26
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {


    @Autowired
    MusicApi musicApi;


    @Override
    public PlaylistVO elicitPlaylist(Long id) throws Exception {

        // 先从数据库中查询是否有列表




        // 没有就从api获取并保存到数据库

        PlaylistDTO playlist = musicApi.playlist(id.toString());



        return null;
    }





}
