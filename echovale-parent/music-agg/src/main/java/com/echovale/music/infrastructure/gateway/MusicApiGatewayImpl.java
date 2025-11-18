package com.echovale.music.infrastructure.gateway;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.appliaction.gateway.MusicApiGateway;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.converter.MusicUrlDetailVOConverter;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicUrlResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 17:11
 */

@Slf4j
@Service("MusicUrlNeteaseProvider")
public class MusicApiGatewayImpl implements MusicApiGateway {

    @Autowired
    private MusicApi musicApi;

    @Autowired
    private MusicUrlDetailVOConverter musicUrlDetailVOConverter;



    @Override
    public List<MusicUrlDetailVO> elicitMusicUrl(List<MusicId> musicIdList, List<NeteaseId> neteaseIdList, String level) throws Exception {

        List<String> neteaseIdListStr = neteaseIdList.stream()
                .map(neteaseId -> neteaseId.getId().toString())
                .toList();


        List<MusicUrlResult> musicUrlResults = musicApi.getMusicV1Url(neteaseIdListStr, level);

        List<MusicUrlDetailVO> musicUrlDetailVOList = new ArrayList<>();

        for (int i = 0; i < musicUrlResults.size(); i++) {
            musicUrlDetailVOList.add(musicUrlDetailVOConverter.byApiResult(musicUrlResults.get(i), musicIdList.get(i)));
        }


        return musicUrlDetailVOList;
    }


    @Override
    public MusicDetailResult elicitMusic(NeteaseId neteaseId) throws Exception {

        List<MusicDetailResult> detail = musicApi.detail(List.of(neteaseId.getNeteaseIdStr()));

        return detail.get(0);
    }




}
