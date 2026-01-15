package com.echovale.playlist.application.service;

import com.echovale.playlist.api.vo.PlaylistVO;
import com.echovale.playlist.application.command.ElicitPlaylistCommand;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/12/1 10:40
 */

@Service
public interface PlaylistApplicationService {
    PlaylistVO elicitPlaylist(ElicitPlaylistCommand command);
}
