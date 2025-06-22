package com.echovale;

import com.echovale.domain.model.MusicModel;
import com.echovale.service.MusicService;
import com.echovale.service.mapping.MusicDetailDTO2ModelMapping;
import com.netease.music.api.autoconfigure.configuration.pojo.dto.MusicDetailDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Author;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.MusicQuality;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @description: TODO 
 * @author 30531
 * @date 2025/6/22 22:17
 * @version 1.0
 */

@Slf4j
@SpringBootTest(classes = MappingTest.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MappingTest {

    @Test
    public void MusicModelMapping() {


//
//        MusicDetailDTO dto = MusicDetailDTO.builder()
//                .id(123L)
//                .no(123123)
//                .fee(123)
//                .name("123")
//                .h(MusicQuality.builder()
//                        .br(111)
//                        .size(333)
//                        .sr(222)
//                        .build())
//                .ar(List.of(Author.builder()
//                        .id(99999L)
//                        .name("99999")
//                        .build(),
//                        Author.builder()
//                        .id(88888L)
//                        .name("88888")
//                        .build()))
//                .originCoverType(1)
//                .publishTime(12345678L)
//                .mv("97123")
//                .build();
//
//        MusicModel model = MusicDetailDTO2ModelMapping.INSTANCE.toModel(dto);
//        log.info("[MappingTest].[MusicModelMapping] model: {}", model);
    }



}
