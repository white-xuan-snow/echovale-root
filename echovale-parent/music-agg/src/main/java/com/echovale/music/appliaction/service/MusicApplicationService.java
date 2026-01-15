package com.echovale.music.appliaction.service;

import com.echovale.music.api.vo.LyricVO;
import com.echovale.music.api.vo.MusicUrlDetailVO;
import com.echovale.music.api.vo.MusicUrlVO;
import com.echovale.music.api.vo.MusicVO;
import com.echovale.music.appliaction.command.ElicitMusicLyricCommand;
import com.echovale.music.appliaction.command.PlayMusicCommand;
import com.echovale.music.appliaction.command.ElicitMusicUrlCommand;
import com.echovale.music.appliaction.dto.MusicDTO;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 30531
 */
@Service
public interface MusicApplicationService {

    List<MusicUrlVO> elicitMusicUrl(ElicitMusicUrlCommand command);

    List<MusicUrlDetailVO> elicitMusicUrlDetail(ElicitMusicUrlCommand command);

    MusicVO playMusic(PlayMusicCommand command);

    LyricVO elicitLyrics(@Valid ElicitMusicLyricCommand command);

    List<MusicVO> saveAndQueryMusicsByExternalTracks(List<MusicDetailResult> tracks);

}
