package com.echovale.root.music.domain;

import com.echovale.music.appliaction.dto.MusicDTO;
import com.echovale.music.domain.service.MusicSupplyService;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.playlist.domain.gateway.PlaylistApiGateway;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/12 20:04
 */

@SpringBootTest
@Slf4j
public class MusicSupplyServiceTest {


    @Autowired
    private MusicSupplyService musicSupplyService;

    @Autowired
    private PlaylistApiGateway playlistApiGateway;



    @Test
    void createAndSaveMusics() {

        List<MusicDetailResult> tracks = playlistApiGateway.getPlaylist(NeteaseId.byLong(19723756L)).getTracks();


        List<MusicDTO> musics = musicSupplyService.createAndSaveMusics(tracks);

        log.info("[MusicSupplyServiceTest].[createAndSaveMusics] musics:{}", musics);


    }


}
