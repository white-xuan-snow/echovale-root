package com.echovale.login.infrastructure.query.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.login.infrastructure.po.UserPO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Component;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 14:15
 */

@Component
public class UserWrapper {







    private MPJLambdaWrapper<UserPO> baseUserJoinWrapper() {
        return new MPJLambdaWrapper<>();
    }
    private LambdaQueryWrapper<UserPO> baseUserQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }


    public LambdaQueryWrapper<UserPO> queryUserByUsernameWrapper(String username) {
        return baseUserQueryWrapper()
                .eq(UserPO::getUsername, username);
    }

    public LambdaQueryWrapper<UserPO> queryUserByPhoneWrapper(String phone) {
        return baseUserQueryWrapper()
                .eq(UserPO::getPhone, phone);
    }

    public LambdaQueryWrapper<UserPO> queryUserByEmailWrapper(String email) {
        return baseUserQueryWrapper()
                .eq(UserPO::getEmail, email);
    }

    public LambdaQueryWrapper<UserPO> queryUserByIdWrapper(UserId userId) {
        return baseUserQueryWrapper()
                .eq(UserPO::getId, userId.getId());
    }
}
