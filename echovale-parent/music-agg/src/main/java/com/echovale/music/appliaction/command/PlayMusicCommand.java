package com.echovale.music.appliaction.command;

import com.echovale.music.appliaction.constant.Message;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 11:51
 */

@Value
@Builder
@AllArgsConstructor
public class PlayMusicCommand {
    Long id;
    Long neteaseId;


    public boolean withoutId() {
        return id == null;
    }


    @AssertTrue(message = Message.Assert.AT_LEAST_ONE_ID_PRESENT)
    public boolean isAtLeastOnIdPresent() {
        return id != null || neteaseId != null;
    }
}
