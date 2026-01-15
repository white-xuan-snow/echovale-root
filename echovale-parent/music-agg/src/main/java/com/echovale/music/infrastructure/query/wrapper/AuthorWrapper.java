package com.echovale.music.infrastructure.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.po.AlbumAuthorsPO;
import com.echovale.music.infrastructure.po.AlbumPO;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.echovale.music.infrastructure.po.MusicAuthorsPO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 16:17
 */


@Component
public class AuthorWrapper {


    public MPJLambdaWrapper<AuthorPO> queryByNeteaseIdWrapper(Long neteaseId) {
        return baseJoinWrapper()
                .eq(AuthorPO::getNeteaseId, neteaseId);
    }

    public MPJLambdaWrapper<AuthorPO> baseJoinWrapper() {
        return new MPJLambdaWrapper<AuthorPO>();
    }


    public MPJLambdaWrapper<AuthorPO> queryByMusicId(MusicId id) {
        return baseJoinWrapper()
                .leftJoin(MusicAuthorsPO.class, MusicAuthorsPO::getAuthorId, AuthorPO::getId)
                .eq(MusicAuthorsPO::getMusicId, id.getId());
    }

    public LambdaQueryWrapper<AuthorPO> queryAuthorsByAuthorIdsWrapper(List<AuthorId> authorIds) {
        return baseQueryWrapper()
                .in(AuthorPO::getId, AuthorId.getIds(authorIds));

    }

    public MPJLambdaWrapper<AuthorPO> queryByAlbumIdWrapper(AlbumId id) {
        return baseJoinWrapper()
                .leftJoin(AlbumAuthorsPO.class, AlbumAuthorsPO::getAlbumId, AlbumPO::getId)
                .eq(AlbumAuthorsPO::getAlbumId, id.getId());
    }

    public MPJLambdaWrapper<AuthorPO> queryExistentIds(List<NeteaseId> authorNeteaseIds) {
        return baseJoinWrapper()
                .select(AuthorPO::getNeteaseId)
                .in(AuthorPO::getNeteaseId, NeteaseId.getLongList(authorNeteaseIds));

    }



    public LambdaQueryWrapper<AuthorPO> baseQueryWrapper() {
        return new LambdaQueryWrapper<AuthorPO>();
    }



}
