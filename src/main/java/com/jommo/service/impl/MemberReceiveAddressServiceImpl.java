package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.common.Status;
import com.jommo.domain.MemberReceiveAddress;
import com.jommo.service.MemberReceiveAddressService;
import com.jommo.mapper.MemberReceiveAddressMapper;
import com.jommo.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 不会开发的小虾米
 * @description 针对表【member_receive_address】的数据库操作Service实现
 * @createDate 2025-04-29 08:34:07
 */
@Service
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressMapper, MemberReceiveAddress>
        implements MemberReceiveAddressService {
    @Autowired
    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    @Override
    public void add(MemberReceiveAddress memberReceiveAddress) {
        Map<String, Object> map = ThreadLocalUtil.get();
        memberReceiveAddress.setMemberId((Long) map.get("id"));
        updateDefaultStatus(memberReceiveAddress);
        memberReceiveAddressMapper.insert(memberReceiveAddress);
    }

    @Override
    public List<MemberReceiveAddress> listByMemberId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaQueryWrapper<MemberReceiveAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberReceiveAddress::getMemberId, memberId);
        queryWrapper.orderByDesc(MemberReceiveAddress::getDefaultStatus);
        return memberReceiveAddressMapper.selectList(queryWrapper);
    }

    @Override
    public void update(MemberReceiveAddress memberReceiveAddress) {
        updateDefaultStatus(memberReceiveAddress);
        memberReceiveAddressMapper.updateById(memberReceiveAddress);
    }

    @Override
    public void updateDefaultStatus(Long id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaUpdateWrapper<MemberReceiveAddress> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MemberReceiveAddress::getMemberId, memberId)
                .ne(MemberReceiveAddress::getId, id)
                .set(MemberReceiveAddress::getDefaultStatus, Status.DISABLED);
        memberReceiveAddressMapper.update(updateWrapper);
        updateWrapper.clear();
        updateWrapper.eq(MemberReceiveAddress::getMemberId, memberId)
                .eq(MemberReceiveAddress::getId, id)
                .set(MemberReceiveAddress::getDefaultStatus, Status.ENABLED);
        memberReceiveAddressMapper.update(updateWrapper);
    }

    private void updateDefaultStatus(MemberReceiveAddress memberReceiveAddress) {
        if (memberReceiveAddress.getDefaultStatus().equals(Status.ENABLED)) {
            LambdaUpdateWrapper<MemberReceiveAddress> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(MemberReceiveAddress::getMemberId, memberReceiveAddress.getMemberId());
            wrapper.set(MemberReceiveAddress::getDefaultStatus, Status.DISABLED);
            memberReceiveAddressMapper.update(wrapper);
        }
    }
}




