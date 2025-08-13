package com.jommo.service;

import com.jommo.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
* @author 不会开发的小虾米
* @description 针对表【member】的数据库操作Service
* @createDate 2025-04-26 17:39:48
*/
public interface MemberService extends IService<Member> {

    Member getByUsername(@NotEmpty(message = "请输入用户名") String username);

    //修改用户头像
    void updateAvatar(@URL String avatarUrl);

    //修改用户昵称
    void updateNickname(String nickname);

}
