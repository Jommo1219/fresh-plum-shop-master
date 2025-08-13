package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.Menu;
import com.jommo.domain.Role;
import com.jommo.domain.RoleMenuRelation;
import com.jommo.mapper.RoleMenuRelationMapper;
import com.jommo.service.RoleService;
import com.jommo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 不会开发的小虾米
 * @description 针对表【role(后台用户角色表)】的数据库操作Service实现
 * @createDate 2025-04-24 11:15:00
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public Role getByName(String name) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, name);
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public void allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        LambdaQueryWrapper<RoleMenuRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenuRelation::getRoleId, roleId);
        roleMenuRelationMapper.delete(queryWrapper);
        //批量插入新关系
        for (Long menuId : menuIds) {
            RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
            roleMenuRelation.setRoleId(roleId);
            roleMenuRelation.setMenuId(menuId);
            roleMenuRelationMapper.insert(roleMenuRelation);
        }
    }

    @Override
    public List<Menu> listMenu(Long roleId) {
        return roleMapper.getMenuListByRoleId(roleId);
    }
}




