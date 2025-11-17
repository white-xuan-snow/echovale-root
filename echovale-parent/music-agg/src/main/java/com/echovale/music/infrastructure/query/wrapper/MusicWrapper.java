package com.echovale.music.infrastructure.query.wrapper;

import com.echovale.music.appliaction.query.dto.MusicIdMappingDTO;
import com.echovale.music.infrastructure.po.MusicPO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 20:06
 */

@Component
public class MusicWrapper {


    public MPJLambdaWrapper<MusicPO> queryMusicDoubleKeyWrapper() {
        return baseMusicWrapper()
                .selectAs(MusicPO::getId, MusicIdMappingDTO::getId)
                .selectAs(MusicPO::getNeteaseId, MusicIdMappingDTO::getNeteaseId);
    }



    public MPJLambdaWrapper<MusicPO> baseMusicWrapper() {
        return new MPJLambdaWrapper<>(MusicPO.class);
    }

}
