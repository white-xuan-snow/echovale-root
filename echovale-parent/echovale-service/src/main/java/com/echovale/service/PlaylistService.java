package com.echovale.service;

import com.echovale.service.vo.PlaylistVO;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistService {
    PlaylistVO elicitPlaylist(Long id);
}
