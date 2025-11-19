package com.echovale.music.api.controller;


import com.echovale.common.domain.infrastructure.presistence.Result;
import com.echovale.music.api.dto.MusicPlayRequest;
import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.dto.MusicUrlRequest;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.service.MusicApplicationService;
import com.echovale.music.infrastructure.converter.PlayMusicCommandConverter;
import com.echovale.music.infrastructure.converter.ElicitMusicUrlCommandConverter;
import jakarta.validation.Valid;
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
    private MusicApplicationService musicApplicationServiceImpl;
    @Autowired
    private ElicitMusicUrlCommandConverter elicitMusicUrlCommandConverter;
    @Autowired
    private PlayMusicCommandConverter playMusicCommandConverter;

    @GetMapping("/url")
    public ResponseEntity<?> elicitMusicUrl(@Valid MusicUrlRequest musicUrlRequest) throws Exception {

        ElicitMusicUrlCommand command = elicitMusicUrlCommandConverter.byMusicUrlRequest(musicUrlRequest);

        List<MusicUrlVO> res = musicApplicationServiceImpl.elicitMusicUrl(command);

        return ResponseEntity.ok(Result.success(res));
    }

    @GetMapping("/url/detail")
    public ResponseEntity<?> elicitMusicUrlDetail(@Valid MusicUrlRequest musicUrlRequest) throws Exception {

        ElicitMusicUrlCommand command = elicitMusicUrlCommandConverter.byMusicUrlRequest(musicUrlRequest);

        List<MusicUrlDetailVO> res = musicApplicationServiceImpl.elicitMusicUrlDetail(command);

        return ResponseEntity.ok(Result.success(res));
    }

    @PostMapping("/play")
    public Result playMusic(@Valid MusicPlayRequest musicPlayRequest) throws Exception {

        PlayMusicCommand playMusicCommand = playMusicCommandConverter.byPlayCommand(musicPlayRequest);

        MusicVO res = musicApplicationServiceImpl.playMusic(playMusicCommand);

        return Result.success(res);
    }

//
//
//    @GetMapping("/lyric")
//    public Result elicitMusicLyric(@RequestParam(value = "id", required = false) Long id,
//                                   @RequestParam(value = "neteaseId", required = false) String neteaseId,
//                                   @RequestParam(value = "types") List<String> types) throws Exception {
//
//        if (id == null && neteaseId == null) {
//            throw new BadRequestException("id与netease为null");
//        }
//
//        LyricVO res = musicService.elicitLyrics(id, neteaseId, types);
//
//
//        return Result.success(res);
//    }



}
