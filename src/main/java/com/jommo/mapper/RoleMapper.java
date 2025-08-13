package com.jommo.mapper;

import com.jommo.domain.Menu;
import com.jommo.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【role(后台用户角色表)】的数据库操作Mapper
* @createDate 2025-04-24 11:15:00
* @Entity com.jommo.domain.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<Menu> getMenuList(Long adminId);

    List<Menu> getMenuListByRoleId(Long roleId);
}




