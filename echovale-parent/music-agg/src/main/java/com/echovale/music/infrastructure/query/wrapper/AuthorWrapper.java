package com.echovale.music.infrastructure.query.wrapper;

import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.echovale.music.infrastructure.po.MusicAuthorsPO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 16:17
 */


@Component
public class AuthorWrapper {


    public MPJLambdaWrapper<AuthorPO> queryByNeteaseIdWrapper(Long neteaseId) {
        return baseWrapper()
                .eq(AuthorPO::getNeteaseId, neteaseId);
    }

    public MPJLambdaWrapper<AuthorPO> baseWrapper() {
        return new MPJLambdaWrapper<AuthorPO>();
    }


    public MPJLambdaWrapper<AuthorPO> queryByMusicId(MusicId id) {
        return baseWrapper()
                .leftJoin(MusicAuthorsPO.class, MusicAuthorsPO::getAuthorId, AuthorPO::getId)
                .eq(MusicAuthorsPO::getMusicId, id.getId());
    }
}
