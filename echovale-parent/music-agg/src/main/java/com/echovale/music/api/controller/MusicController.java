package com.echovale.music.api.controller;


import com.echovale.common.domain.infrastructure.presistence.Result;
import com.echovale.music.api.dto.MusicIncrementRequest;
import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.dto.MusicUrlRequest;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.AddMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.service.MusicApplicationService;
import com.echovale.music.infrastructure.converter.AddMusicCommandConverter;
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
    private MusicApplicationService musicApplicationService;
    @Autowired
    private ElicitMusicUrlCommandConverter elicitMusicUrlCommandConverter;
    @Autowired
    private AddMusicCommandConverter addMusicCommandConverter;

    @GetMapping("/url")
    public ResponseEntity<?> elicitMusicUrl(@Valid MusicUrlRequest musicUrlRequest) throws Exception {

        ElicitMusicUrlCommand command = elicitMusicUrlCommandConverter.byMusicUrlRequest(musicUrlRequest);

        List<MusicUrlVO> res = musicApplicationService.elicitMusicUrl(command);

        return ResponseEntity.ok(Result.success(res));
    }

    @GetMapping("/url/detail")
    public ResponseEntity<?> elicitMusicUrlDetail(@Valid MusicUrlRequest musicUrlRequest) throws Exception {

        ElicitMusicUrlCommand command = elicitMusicUrlCommandConverter.byMusicUrlRequest(musicUrlRequest);

        List<MusicUrlDetailVO> res = musicApplicationService.elicitMusicUrlDetail(command);

        return ResponseEntity.ok(Result.success(res));
    }

    @PostMapping("/new")
    public Result incrementMusic(@Valid MusicIncrementRequest musicIncrementRequest) throws Exception {

        AddMusicCommand addMusicCommand = addMusicCommandConverter.byIncrementCommand(musicIncrementRequest);

        MusicVO res = musicApplicationService.addMusic(addMusicCommand);

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
