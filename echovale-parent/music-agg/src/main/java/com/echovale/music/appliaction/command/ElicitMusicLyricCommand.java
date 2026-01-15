package com.echovale.music.appliaction.command;

import com.echovale.music.appliaction.constant.Message;
import com.echovale.music.appliaction.validation.MusicLyricTypeConstraint;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/27 18:11
 */

@Value
@Builder
@AllArgsConstructor
public class ElicitMusicLyricCommand {
    Long id;
    Long neteaseId;
    @MusicLyricTypeConstraint
    List<String> types;


    @AssertTrue(message = Message.Assert.AT_LEAST_ONE_ID_PRESENT)
    public boolean isAtLeastOneIdPresent() {
        return id != null || neteaseId != null;
    }

    public Boolean isIdExist() {
        return id != null;
    }


}
