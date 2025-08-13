package com.jommo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.UserAddress;
import com.jommo.service.UserAddressService;
import com.jommo.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

/**
* @author 不会开发的小虾米
* @description 针对表【user_address】的数据库操作Service实现
* @createDate 2025-04-04 16:18:58
*/
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
    implements UserAddressService{

}




