package com.echovale.api.controller.client;

import com.echovale.service.dto.Result;
import com.netease.music.api.autoconfigure.configuration.api.MusicApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/7 22:05
 */
@CrossOrigin
@RestController
public class TestController {


    @Autowired
    MusicApi musicApi;

    @GetMapping("/lyric/{id}")
    public Result lyricApiTest(@PathVariable String id) throws Exception {
        return Result.success(musicApi.getLyrics(id));
    }



}
