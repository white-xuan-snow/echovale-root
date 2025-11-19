package com.echovale.music.appliaction.service;

import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 */
@Service
public interface MusicApplicationService {

    List<MusicUrlVO> elicitMusicUrl(ElicitMusicUrlCommand command) throws Exception;

    List<MusicUrlDetailVO> elicitMusicUrlDetail(ElicitMusicUrlCommand command) throws Exception;

    MusicVO playMusic(PlayMusicCommand command) throws Exception;

}
