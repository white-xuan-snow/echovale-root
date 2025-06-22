package com.echovale.api;

import com.echovale.domain.model.MusicModel;
import com.echovale.service.mapping.MusicDetailDTO2ModelMapping;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicDetailDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Author;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.MusicQuality;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/22 22:50
 */
//@Slf4j
@SpringBootTest
public class MappingTest {

//    @Resource
//    private MusicDetailDTO2ModelMapping musicDetailDTO2ModelMapping;


    @Test
    public void MusicModelMapping() {

        MusicDetailDTO dto = MusicDetailDTO.builder()
                .id(123L)
                .no(123123)
                .fee(123)
                .name("123")
                .h(MusicQuality.builder()
                        .br(111)
                        .size(333)
                        .sr(222)
                        .build())
                .ar(List.of(Author.builder()
                                .id(99999L)
                                .name("99999")
                                .build(),
                        Author.builder()
                                .id(88888L)
                                .name("88888")
                                .build()))
                .originCoverType(1)
                .publishTime(12345678L)
                .mv("97123")
                .build();

//        MusicModel model = musicDetailDTO2ModelMapping.toModel(dto);
//        log.info("[MappingTest].[MusicModelMapping] model: {}", model);
    }
}
