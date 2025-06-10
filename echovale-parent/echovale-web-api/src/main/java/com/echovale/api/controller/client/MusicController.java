package com.echovale.api.controller.client;

import com.echovale.domain.model.Result;
import com.echovale.service.MusicService;
import com.echovale.service.vo.MusicUrlVO;
import org.springframework.beans.factory.annotation.Autowired;
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
    MusicService musicService;

    @GetMapping("/url")
    public Result getMusicUrl(@RequestParam List<String> ids, @RequestParam String level) throws Exception {

        List<MusicUrlVO> res = musicService.elicitMusicUrl(ids, level);


        return Result.success();
    }


}
