package com.echovale.api.controller.client;

import com.echovale.common.exception.BadRequestException;
import com.echovale.domain.po.AuthorPO;
import com.echovale.service.AuthorService;
import com.echovale.service.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/16 20:16
 */

@CrossOrigin
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/desc")
    public Result elicitAuthorDesc(@RequestParam(value = "id", required = false) Long id,
                               @RequestParam(value = "neteaseId", required = false) String neteaseId) {

        if (id == null && neteaseId == null) {
            throw new BadRequestException("id与neteaseId");
        }

        AuthorPO res = authorService.elicitAuthorDesc(id, neteaseId);

        return Result.success(res);
    }

}
