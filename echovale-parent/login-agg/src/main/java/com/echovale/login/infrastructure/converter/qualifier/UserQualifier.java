package com.echovale.login.infrastructure.converter.qualifier;

import com.echovale.login.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 15:50
 */

@Component
@RequiredArgsConstructor
public class UserQualifier {



    public UserId mapUserId(Long res) {
        return UserId.of(res);
    }



}
