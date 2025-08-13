package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.Member;
import com.jommo.service.MemberService;
import com.jommo.mapper.MemberMapper;
import com.jommo.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 不会开发的小虾米
 * @description 针对表【member】的数据库操作Service实现
 * @createDate 2025-04-26 17:39:48
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
        implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member getByUsername(String username) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getUsername, username);
        return memberMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaUpdateWrapper<Member> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Member::getId, memberId);
        updateWrapper.set(Member::getAvatar, avatarUrl);
        memberMapper.update(updateWrapper);
    }

    @Override
    public void updateNickname(String nickname) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaUpdateWrapper<Member> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Member::getId, memberId);
        updateWrapper.set(Member::getNickname, nickname);
        memberMapper.update(updateWrapper);
    }
}




