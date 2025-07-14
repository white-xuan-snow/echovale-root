package com.echovale.api.controller.client;

import com.echovale.service.dto.Result;
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
    public Result elicitMusicUrl(@RequestParam(value = "ids", required = false) List<Long> ids,
                                 @RequestParam(value = "neteaseIds", required = false) List<String> neteaseIds,
                                 @RequestParam String level) throws Exception {

        // TODO level取值检测

        List<String> res = musicService.elicitMusicUrl(ids, neteaseIds, level);

        return Result.success(res);
    }



}
