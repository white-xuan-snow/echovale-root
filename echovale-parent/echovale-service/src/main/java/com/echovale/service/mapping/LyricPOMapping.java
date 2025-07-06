package com.echovale.service.mapping;


import com.echovale.domain.po.LyricPO;
import com.echovale.service.config.MappingConfig;
import com.netease.music.api.autoconfigure.configuration.pojo.result.LyricsResult;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class,
    componentModel = "spring"
)
public abstract class LyricPOMapping {


    public LyricPO byResult(LyricsResult res, Long id) {
        return coreResult(res, LyricPO.builder().musicId(id).build());
    }

    public LyricPO byResult(LyricsResult res, LyricPO po) {
        return coreResult(res, po);
    }


    private LyricPO coreResult(LyricsResult res, LyricPO po) {
        // lrc歌词
        po.setNeteaseLrc(res.getLrc());
        // lyc翻译歌词
        po.setNeteaseTlrc(res.getTlyric());
        // 网易云逐字歌词
        po.setNeteaseYrc(res.getYrc());
        // 未知
        po.setNeteaseKlrc(res.getKlyric());
        // 罗马音歌词
        po.setNeteaseRomalyc(res.getRomalrc());

        return po;
    }



}
