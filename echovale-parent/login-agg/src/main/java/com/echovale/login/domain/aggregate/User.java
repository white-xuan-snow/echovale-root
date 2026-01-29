package com.echovale.login.domain.aggregate;

import com.echovale.login.domain.entity.UserInfoExt;
import com.echovale.login.domain.entity.UserMetaExt;
import com.echovale.login.domain.valueobject.UserId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/15 22:41
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    UserId id;
    String username;
    @JsonIgnore
    String password;
    String email;
    String phone;
    UserMetaExt userMetaExt;
    UserInfoExt userInfoExt;

    public static boolean isNull(User user) {
        return user == null;
    }

    public static User onlySetPassword(String s) {
        return User.builder()
                .id(new UserId())
                .password(s).build();
    }
}
