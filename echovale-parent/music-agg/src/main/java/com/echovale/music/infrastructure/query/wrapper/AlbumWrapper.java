package com.echovale.music.infrastructure.query.wrapper;

import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.infrastructure.po.AlbumPO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 20:37
 */
@Component
public class AlbumWrapper {


    public MPJLambdaWrapper<AlbumPO> queryAlbumById(AlbumId albumId) {
        return baseWrapper()
                .eq(AlbumPO::getId, albumId.getId());
    }


    public MPJLambdaWrapper<AlbumPO> baseWrapper() {
        return new MPJLambdaWrapper<AlbumPO>();
    }


}
