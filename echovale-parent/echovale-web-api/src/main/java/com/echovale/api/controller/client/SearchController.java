package com.echovale.api.controller.client;

import com.echovale.common.constants.param.SearchParam;
import com.echovale.common.exception.BadRequestException;
import com.echovale.service.SearchService;
import com.echovale.service.dto.Result;
import com.echovale.service.vo.SearchVO;
import com.netease.music.api.autoconfigure.configuration.module.MusicModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/16 19:44
 */
@CrossOrigin
@RestController()
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;


    @GetMapping("/netease")
    public Result neteaseSearch(@RequestParam(value = "content", required = false) String content,
                                @RequestParam(value = "limit", required = false) Integer limit,
                                @RequestParam(value = "offset", required = false) Integer offset,
                                @RequestParam(value = "type", required = false) Integer type) throws Exception {
        if (content == null || content.isEmpty()) {
            throw new BadRequestException("content");
        }
        limit = limit == null ? SearchParam.Limit : limit;
        offset = offset == null ? SearchParam.Offset : offset;
        type = type == null ? SearchParam.Type : type;

        if (limit < 1) {
            throw new BadRequestException("limit");
        }
        if (offset < 0) {
            throw new BadRequestException("offset");
        }
        if (!SearchParam.TypeSet.contains(type)) {
            throw new BadRequestException("type: " + type);
        }

        SearchVO res = searchService.neteaseSearch(content, limit, offset, type);

        return Result.success(res);
    }



}
