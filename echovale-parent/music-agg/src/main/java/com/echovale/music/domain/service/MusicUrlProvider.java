package com.echovale.music.domain.service;

import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicUrlResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 17:11
 */

@Slf4j
@Service("MusicUrlNeteaseProvider")
public class MusicUrlProvider {

    @Autowired
    private MusicApi musicApi;

    public List<String> elicitMusicUrl(List<Long> musicIdList, String level) throws Exception {

        List<String> neteaseIds = musicIdList.stream()
                .map(String::valueOf)
                .toList();


        List<MusicUrlResult> musicUrlResults = musicApi.getMusicV1Url(neteaseIds, level);

        return musicUrlResults.stream()
                .map(MusicUrlResult::getUrl)
                .toList();
    }
}
