package com.echovale.service;


import com.echovale.service.vo.MusicUrlVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicService {
    List<MusicUrlVO> elicitMusicUrl(List<String> ids, String level) throws Exception;
}
