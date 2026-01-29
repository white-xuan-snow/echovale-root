package com.echovale.login.infrastructure.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.echovale.login.domain.aggregate.User;
import com.echovale.login.domain.valueobject.UserId;
import com.echovale.login.infrastructure.converter.UserConverter;
import com.echovale.login.infrastructure.mapper.UserMapper;
import com.echovale.login.infrastructure.po.UserPO;
import com.echovale.login.infrastructure.query.wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/17 14:10
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryService {


    private final UserMapper userMapper;
    private final UserWrapper userWrapper;
    private final UserConverter userConverter;


    public User queryUserByUsername(String username) {

        LambdaQueryWrapper<UserPO> wrapper = userWrapper.queryUserByUsernameWrapper(username);

        UserPO userPO = userMapper.selectOne(wrapper);

        return userConverter.byPO(userPO);
    }

    public User queryUserByPhone(String phone) {
        LambdaQueryWrapper<UserPO> wrapper = userWrapper.queryUserByPhoneWrapper(phone);

        UserPO userPO = userMapper.selectOne(wrapper);

        return userConverter.byPO(userPO);

    }

    public User queryUserByEmail(String email) {
        LambdaQueryWrapper<UserPO> wrapper = userWrapper.queryUserByEmailWrapper(email);

        UserPO userPO = userMapper.selectOne(wrapper);

        return userConverter.byPO(userPO);
    }

    public User queryUserById(UserId userId) {
        LambdaQueryWrapper<UserPO> wrapper = userWrapper.queryUserByIdWrapper(userId);

        UserPO userPO = userMapper.selectOne(wrapper);

        return userConverter.byPO(userPO);
    }
}
