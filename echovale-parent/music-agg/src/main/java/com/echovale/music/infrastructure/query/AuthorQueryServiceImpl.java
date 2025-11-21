package com.echovale.music.infrastructure.query;

import com.echovale.music.appliaction.query.AuthorQueryService;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.valueobject.AlbumId;
import com.echovale.music.domain.valueobject.AuthorId;
import com.echovale.music.domain.valueobject.MusicId;
import com.echovale.music.infrastructure.converter.AuthorConverter;
import com.echovale.music.infrastructure.mapper.AuthorMapper;
import com.echovale.music.infrastructure.po.AuthorPO;
import com.echovale.music.infrastructure.query.wrapper.AuthorWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/19 20:20
 */


@Slf4j
@Service
public class AuthorQueryServiceImpl implements AuthorQueryService {


    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorWrapper authorWrapper;

    @Autowired
    private AuthorConverter authorConverter;

    @Override
    public List<Author> queryAuthorsByMusicId(MusicId id) {

        // 联表查List<AuthorPO>

        MPJLambdaWrapper<AuthorPO> wrapper = authorWrapper.queryByMusicId(id);

        List<AuthorPO> authorPOList = authorMapper.selectJoinList(wrapper);

        log.info("[AuthorQueryServiceImpl].[queryAuthorsByMusicId] 通过音乐id：{} 查询到{}个作者", id.getId(), authorPOList.size());

        return authorPOList.stream()
                .map(authorConverter::byPO)
                .toList();
    }

    @Override
    public List<Author> queryAuthorsByIds(List<AuthorId> authorIds) {


        MPJLambdaWrapper<AuthorPO> wrapper = authorWrapper.queryByIds(authorIds);

        List<AuthorPO> authorPOList = authorMapper.selectJoinList(wrapper);

        log.info("[AuthorQueryServiceImpl].[queryAuthorsByIds] 通过作者ids：{} 查询到{}个作者", AuthorId.getIds(authorIds), authorPOList.size());

        return authorPOList.stream()
                .map(authorConverter::byPO)
                .toList();
    }

    @Override
    public List<Author> queryAuthorsByAlbumId(AlbumId id) {

        MPJLambdaWrapper<AuthorPO> wrapper = authorWrapper.queryByAlbumIdWrapper(id);

        List<AuthorPO> authorPOList = authorMapper.selectJoinList(wrapper);

        return authorPOList.stream()
                .map(authorConverter::byPO)
                .toList();
    }
}
