package com.jommo.service;

import com.jommo.domain.MemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【member_receive_address】的数据库操作Service
* @createDate 2025-04-29 08:34:07
*/
public interface MemberReceiveAddressService extends IService<MemberReceiveAddress> {

    //添加收货地址
    void add(MemberReceiveAddress memberReceiveAddress);

    //根据用户id查询收货地址
    List<MemberReceiveAddress> listByMemberId();

    //修改收货地址
    void update(MemberReceiveAddress memberReceiveAddress);

    //修改默认地址
    void updateDefaultStatus(Long id);
}
