package com.echovale.music.api.dto;

import com.echovale.music.api.validation.MusicLevelConstraint;
import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 21:07
 */
@Value
@Builder
public class MusicUrlRequest {
    List<Long> ids;
    List<Long> neteaseIds;
    @MusicLevelConstraint
    String level;

    @AssertTrue(message = "ids或neteaseIds请至少提供一个")
    public boolean isAtLeastOneIdPresent() {
        boolean isIdsPresent = ids != null && !ids.isEmpty();
        boolean isNeteaseIdsPresent = neteaseIds != null && !neteaseIds.isEmpty();
        return isIdsPresent || isNeteaseIdsPresent;
    }
}
