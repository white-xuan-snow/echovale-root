package com.echovale.music.appliaction.command;

import com.echovale.music.appliaction.constant.Message;
import com.echovale.music.appliaction.validation.MusicLevelConstraint;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 16:41
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElicitMusicUrlCommand {
    List<Long> ids;
    List<Long> neteaseIds;
    @MusicLevelConstraint
    String level;


    public boolean withoutNeteaseIds() {
        return neteaseIds == null || neteaseIds.isEmpty();
    }

    @AssertTrue(message = Message.Assert.AT_LEAST_ONE_ID_PRESENT)
    public boolean isAtLeastOneIdPresent() {
        boolean isIdsPresent = ids != null && !ids.isEmpty();
        boolean isNeteaseIdsPresent = neteaseIds != null && !neteaseIds.isEmpty();
        return isIdsPresent || isNeteaseIdsPresent;
    }

}
