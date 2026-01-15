package com.echovale.shared.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public enum ActivityStatus {
    DELETED(0),
    HALF_FILLED(1),
    FULL(2),
    INACTIVE(3),
    ACTIVE(4)
    ;

    private final int level;



    public static Boolean isActive(ActivityStatus status) {
        return status == ActivityStatus.ACTIVE;
    }
    public static Boolean isDeleted(ActivityStatus status) {
        return status == ActivityStatus.DELETED;
    }
    public static Boolean isFull(ActivityStatus status) {
        return status.getLevel() >= ActivityStatus.FULL.getLevel();
    }

    public static ActivityStatus of(Integer status) {
        return ActivityStatus.values()[status];
    }
}
