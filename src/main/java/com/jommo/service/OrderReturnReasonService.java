package com.jommo.service;

import com.jommo.domain.OrderReturnReason;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotEmpty;

/**
* @author 不会开发的小虾米
* @description 针对表【order_return_reason】的数据库操作Service
* @createDate 2025-04-25 11:42:39
*/
public interface OrderReturnReasonService extends IService<OrderReturnReason> {

    //根据原因类型查找
    OrderReturnReason getByName(@NotEmpty(message = "请输入原因类型") String name);
}
