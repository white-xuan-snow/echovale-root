package com.echovale.music.api.dto;

import com.echovale.music.api.constant.Message;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/18 11:50
 */
@Value
@Builder
@AllArgsConstructor
public class MusicPlayRequest {
    Long id;
    Long neteaseId;

    @AssertTrue(message = Message.Assert.AT_LEAST_ONE_ID_PRESENT)
    public boolean isAtLeastOnIdPresent() {
        return id != null || neteaseId != null;
    }
}
