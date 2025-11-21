package com.echovale.music.infrastructure.query.wrapper;

import com.echovale.music.appliaction.dto.MusicIdMapping;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.domain.valueobject.NeteaseId;
import com.echovale.music.infrastructure.po.AuthorPO;
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


    public MPJLambdaWrapper<MusicPO> queryMusicIdsWrapper() {
        return baseMusicWrapper()
                .selectAs(MusicPO::getId, MusicIdMapping::getId)
                .selectAs(MusicPO::getNeteaseId, MusicIdMapping::getNeteaseId);
    }

    public MPJLambdaWrapper<MusicPO> queryMusicIdByNeteaseIdWrapper(Long neteaseId) {
        return baseMusicWrapper()
                .select(MusicPO::getId)
                .eq(MusicPO::getNeteaseId, neteaseId);
    }








    public MPJLambdaWrapper<MusicPO> baseMusicWrapper() {
        return new MPJLambdaWrapper<>(MusicPO.class);
    }


    public MPJLambdaWrapper<AuthorPO> baseAuthorWrapper() {
        return new MPJLambdaWrapper<>(AuthorPO.class);
    }

    public MPJLambdaWrapper<MusicPO> queryMusicByIdsWrapper(MusicId musicId, NeteaseId neteaseId) {

        return baseMusicWrapper()
                .eq(MusicPO::getId, musicId.getId())
                .or()
                .eq(MusicPO::getNeteaseId, neteaseId.getId());

    }

    public MPJLambdaWrapper<MusicPO> queryNeteaseIdByIdWrapper(MusicId id) {
        return baseMusicWrapper()
                .select(MusicPO::getNeteaseId)
                .eq(MusicPO::getId, id.getId());
    }
}
