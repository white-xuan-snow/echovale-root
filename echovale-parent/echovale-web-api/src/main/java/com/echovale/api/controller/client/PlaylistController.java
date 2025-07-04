package com.echovale.api.controller.client;

import com.echovale.service.dto.Result;
import com.echovale.service.PlaylistService;
import com.echovale.service.vo.PlaylistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/12 15:16
 */

@CrossOrigin
@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    @PostMapping()
    public Result getPlaylist(@RequestParam("id") Long id) throws Exception {

        PlaylistVO res = playlistService.elicitPlaylist(id);

        // TODO 结果处理
 
        return Result.success();
    }


}
