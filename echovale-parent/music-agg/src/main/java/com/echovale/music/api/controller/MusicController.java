package com.echovale.music.api.controller;


import com.echovale.shared.infrastructure.presistence.Result;
import com.echovale.music.api.dto.MusicLyricRequest;
import com.echovale.music.api.dto.MusicPlayRequest;
import com.echovale.music.api.vo.LyricVO;
import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.dto.MusicUrlRequest;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.ElicitMusicLyricCommand;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.service.MusicApplicationService;
import com.echovale.music.infrastructure.converter.MusicCommandConverter;
import com.echovale.music.infrastructure.converter.PlayMusicCommandConverter;
import com.echovale.music.infrastructure.converter.ElicitMusicUrlCommandConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 13:32
 */

@CrossOrigin
@RestController
@RequestMapping("/music")
public class MusicController {


    @Autowired
    private MusicApplicationService musicApplicationService;
    @Autowired
    private ElicitMusicUrlCommandConverter elicitMusicUrlCommandConverter;
    @Autowired
    private PlayMusicCommandConverter playMusicCommandConverter;

    @Autowired
    private MusicCommandConverter musicCommandConverter;


    @GetMapping("/url")
    public ResponseEntity<?> elicitMusicUrl(MusicUrlRequest musicUrlRequest) {

        ElicitMusicUrlCommand command = elicitMusicUrlCommandConverter.byRequest(musicUrlRequest);

        List<MusicUrlVO> res = musicApplicationService.elicitMusicUrl(command);

        return ResponseEntity.ok(Result.success(res));
    }

    @GetMapping("/url/detail")
    public ResponseEntity<?> elicitMusicUrlDetail(MusicUrlRequest musicUrlRequest) {

        ElicitMusicUrlCommand command = elicitMusicUrlCommandConverter.byRequest(musicUrlRequest);

        List<MusicUrlDetailVO> res = musicApplicationService.elicitMusicUrlDetail(command);

        return ResponseEntity.ok(Result.success(res));
    }

    @PostMapping("/play")
    public ResponseEntity<?> playMusic(MusicPlayRequest musicPlayRequest) {

        PlayMusicCommand playMusicCommand = playMusicCommandConverter.byPlayCommand(musicPlayRequest);

        MusicVO res = musicApplicationService.playMusic(playMusicCommand);

        return ResponseEntity.ok(Result.success(res));
    }



    @GetMapping("/lyric")
    public ResponseEntity<?> elicitMusicLyric(MusicLyricRequest musicLyricRequest) {
        // TODO 参数验证@Validated
        ElicitMusicLyricCommand command = musicCommandConverter.byRequest(musicLyricRequest);

        LyricVO res = musicApplicationService.elicitLyrics(command);

        return ResponseEntity.ok(Result.success(res));
    }



}
