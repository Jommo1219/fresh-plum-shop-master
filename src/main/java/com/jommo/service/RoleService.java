package com.jommo.service;

import com.jommo.domain.Menu;
import com.jommo.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【role(后台用户角色表)】的数据库操作Service
* @createDate 2025-04-24 11:15:00
*/
public interface RoleService extends IService<Role> {

    //根据名字查询角色
    Role getByName(@NotEmpty(message = "请输入角色名称") String name);

    //分配菜单
    void allocMenu(Long roleId, List<Long> menuIds);

    //获取当前角色的菜单
    List<Menu> listMenu(Long roleId);
}
