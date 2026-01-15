package com.echovale.playlist.api.controller;

import com.echovale.common.domain.infrastructure.presistence.Result;
import com.echovale.playlist.api.dto.PlaylistRequest;
import com.echovale.playlist.api.vo.PlaylistVO;
import com.echovale.playlist.application.command.ElicitPlaylistCommand;
import com.echovale.playlist.application.service.PlaylistApplicationService;
import com.echovale.playlist.infrastructure.converter.PlaylistRequestConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 10:30
 */


@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRequestConverter playlistRequestConverter;

    @Autowired
    private PlaylistApplicationService playlistApplicationService;


    @PostMapping()
    public ResponseEntity<?> elicitPlaylist(PlaylistRequest playlistRequest) {

        ElicitPlaylistCommand command = playlistRequestConverter.byRequest(playlistRequest);

        PlaylistVO res = playlistApplicationService.elicitPlaylist(command);

        return ResponseEntity.ok(Result.success(res));
    }



}
