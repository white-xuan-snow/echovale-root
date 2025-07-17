package com.echovale.api;

import com.echovale.service.dto.AlbumDTO;
import com.echovale.service.dto.MusicDTO;
import com.echovale.service.mapping.AlbumDTOMapping;
import com.echovale.service.mapping.MusicDTOMapping;
import com.netease.music.api.autoconfigure.configuration.api.AlbumApi;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.Author;
import com.netease.music.api.autoconfigure.configuration.pojo.entity.MusicQuality;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumListResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.ChorusResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import com.netease.music.api.autoconfigure.configuration.util.Init;
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
@Slf4j
@SpringBootTest
public class MappingTest {

    @Autowired
    private MusicDTOMapping musicDTOMapping;

    @Autowired
    Init init;
    @Autowired
    AlbumApi albumApi;


    private void initCookie() {
        init.setLocalCookie("NMTID=00OmhC3rhWqkmkbvk9csdH5XCadI04AAAGRUUDO6Q; _ntes_nnid=b218cfe313c2dcaa54feb2e6f0f91f2e,1723641846099; _ntes_nuid=b218cfe313c2dcaa54feb2e6f0f91f2e; WEVNSM=1.0.0; WNMCID=ffvouj.1723641846662.01.0; __snaker__id=qArApKgRaNMTh66w; sDeviceId=YD-hFpKMuojW8tFE1AQEUbWQzdDMECjOkKA; WM_TID=%2BmrdELgLqVhEAVUFFELXFjIXYECiPHGb; ntes_kaola_ad=1; _ga=GA1.1.127948830.1725215043; _ga_EPDQHDTJH5=GS1.1.1736092978.1.1.1736092982.0.0.0; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2219570f08245b40-06d2e908a380bcc-4c657b58-1384801-19570f082461609%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E8%87%AA%E7%84%B6%E6%90%9C%E7%B4%A2%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.bing.com%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk1NzBmMDgyNDViNDAtMDZkMmU5MDhhMzgwYmNjLTRjNjU3YjU4LTEzODQ4MDEtMTk1NzBmMDgyNDYxNjA5In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%7D; _clck=12hgay3%7C2%7Cfu0%7C0%7C1705; Qs_lvt_382223=1725215042%2C1725693790%2C1741356566%2C1741356604; Qs_pv_382223=3237204859810105000%2C2747737008162744300%2C3241285445959108600%2C4114217228974340600%2C3033387302837452000; _ga_C6TGHFPQ1H=GS1.1.1741361273.5.0.1741361273.0.0.0; ntes_utid=tid._.lng%252B5Ru9nVBEFhUAEEKHQjJDNVG3PgZ3._.0.%2C.edd._.._.0; __remember_me=true; P_INFO=15523532975|1751542274|1|music|00&99|null&null&null#chq&null#10#0|&0||15523532975; JSESSIONID-WYYY=789%2FR%2FFYX2JwpSj7nnTcMl5ZNs%2BmIBEuG7Bp3bzl7aQt%2By0sSWKMgKVAKn%2BTMjsPyArC0uwYf%2FHc%5CKlS5E0mj%5Cfnj6R3hIu0I8ze2jGPK9RKMZj4xJn67RaIseUGKHdEMo2GNM4ZOhOuyoUjcaeE4g4Um0VnpoxjtRtibuXbMGqj%2Fqtb%3A1751889785699; _iuqxldmzr_=32; WM_NI=7KqLjPgiGYwQBKIM7vYnGzVZRaoAdvigYE0JH9qvGCR7U2usvv3SuPXYZwSod5Pgxt5SGUn6w4zwz%2Bh%2BckTaBWPSoK44PT8wXr5p3zSORchNsiFB8t6vlj3vGXVvZgimZ2I%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed3bb7297e9e5a7cc808cb08fb2d15a838a9eaddb6ab7a8a48bcd46ba95a995d82af0fea7c3b92af595bfade95ca5b789baf65b829587aeb67e9bb2899bd55292bfa1a2e77eabb997a6b3428ce7bf97c64992e99bd3f879b6aafdb4f85c9c879698d265a8eba088fb80f78d89b6ed3eb5ecb698f921acf5ad8cd76b8289a0b4ee41f8eca0b5bb7d9caefb91b27291919e84eb46b1b78aa3bb80f6aaa48bb27e85889a97d96dabb396d3cc37e2a3; gdxidpyhxdE=WnCTqapwbsh1%2FcSx690WorQd8t7bT%2BYizZRmAsn1SmKoXbzENEKU7%5CJOoLlznzmLXrp7AQ4BcoAiyV7XRtNwLIRxueAc8Nw14hEbqCa1u1S8tgekKJQ8kDQ1OursDKwaKp8%5CrPaeeun%2F%2BSglAD9S3z9kWvgRVMWQobHauZyLkyxMENdk%3A1751888888675; __csrf=166df5f91a95c2219e36b445ac525eb1; MUSIC_U=0081F38E94A5528C8F0DF634B79EB47A476A780F382F8F9CE9E84326AAA55FF756893BDFFF801E3FF95C11A1C663B2560516AD031CE00065C3031B67F6BCF73CB9DE5091F167FCF338C3E6277F8A7CEA9CBD46F1284C231EBD6727B30CECA3560DA92B004B7333F37B7945477BA8CE76732C5E543757ECB4967FA457213984CEF519CB708A7514D896FDCBC92CC1FEA3604FFA9C617A9271EE20D6213F2B9D9B92CABFC519DE16168F1999BFE0BDB1DEE8EDD55F10B593B7C248968136FBB277FA2F7184E60F05AF8C98272406DA0F550EA3619E706348238CDA40409E267B573E0A92DDDDF8340630FE65246A7CAA74DBBA1CAF08E10C894CE5F96D777EE3E0CAFCF90C7C9C0EA1955EAD90DDB1F7617E89C7606374FDE03D316DAA8FF8550AC1768E2288D1872594529F8F17AD5A62E8C2EDE9DF6171276E50E2CEB42AE6D601A22A7E3F3583C8FD61EE23D627AA4C781411584CB3E07189E4C8ADE7C60887EA");
    }


    @Test
    public void MusicModelMapping() {

        MusicDetailResult dto = MusicDetailResult.builder()
                .id(123L)
                .no(123123)
                .fee(123)
                .name("123")
                .m(MusicQuality.builder()
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

        ChorusResult chorusDTO = ChorusResult.builder()
                .startTime(123)
                .endTime(456)
                .build();

        MusicDTO model = musicDTOMapping.byDetailResult(dto);
        musicDTOMapping.chorusToModel(chorusDTO, model);

        log.info("[MappingTest].[MusicDTOMapping] model: {}", model);
    }

    @Autowired
    AlbumDTOMapping albumDTOMapping;

    @Test
    public void AlbumDTOMappingTest() throws Exception {
        initCookie();
        AlbumListResult albumListResult = albumApi.album("164565202");

        AlbumDTO albumDTO = albumDTOMapping.byResult(albumListResult.getAlbum());

        log.info("[MappingTest].[AlbumDTOMappingTest] albumDTO: {}", albumDTO);

    }
}
