package com.echovale.service.impl;

import com.echovale.service.MusicOrchestrator;
import com.echovale.service.SearchService;
import com.echovale.service.dto.MusicDTO;
import com.echovale.service.mapping.MusicDTOMapping;
import com.echovale.service.vo.SearchVO;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.module.MusicModule;
import com.netease.music.api.autoconfigure.configuration.pojo.result.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/16 19:45
 */

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    MusicApi musicApi;

    @Autowired
    MusicOrchestrator musicOrchestrator;


    @Override
    public SearchVO neteaseSearch(String content, Integer limit, Integer offset, Integer type) throws Exception {

        SearchResult searchResult = musicApi.search(content, limit, offset, type);

        List<MusicDTO> musicDTOList = musicOrchestrator.updateMusics(searchResult.getSongs());

        return SearchVO.builder()
                .songCount(searchResult.getSongCount())
                .neteaseMusics(musicDTOList)
                .build();
    }
}
