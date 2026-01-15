package com.echovale.music.infrastructure.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.po.AlbumPO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 20:37
 */
@Component
public class AlbumWrapper {


    public MPJLambdaWrapper<AlbumPO> queryAlbumById(AlbumId albumId) {
        return baseJoinWrapper()
                .eq(AlbumPO::getId, albumId.getId());
    }


    public MPJLambdaWrapper<AlbumPO> baseJoinWrapper() {
        return new MPJLambdaWrapper<AlbumPO>();
    }


    public LambdaQueryWrapper<AlbumPO> baseQueryWrapper() {
        return new LambdaQueryWrapper<AlbumPO>();
    }

    public MPJLambdaWrapper<AlbumPO> queryNonexistentAlbumNeteaseIds(List<NeteaseId> albumNeteaseIds) {
        return baseJoinWrapper()
                .select(AlbumPO::getNeteaseId)
                .in(AlbumPO::getNeteaseId, albumNeteaseIds);


    }

    public LambdaQueryWrapper<AlbumPO> queryAlbumsByAlbumIdsWrapper(List<AlbumId> albumIds) {
        return baseQueryWrapper()
                .in(AlbumPO::getId, albumIds);
    }
}
