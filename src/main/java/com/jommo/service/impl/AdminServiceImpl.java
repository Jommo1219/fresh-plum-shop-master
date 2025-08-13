package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.Admin;
import com.jommo.domain.AdminRoleRelation;
import com.jommo.domain.Menu;
import com.jommo.domain.Role;
import com.jommo.dto.AdminUpdateDTO;
import com.jommo.mapper.*;
import com.jommo.service.AdminService;
import com.jommo.util.Md5Util;
import com.jommo.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 不会开发的小虾米
 * @description 针对表【admin(管理员表)】的数据库操作Service实现
 * @createDate 2025-04-23 15:28:37
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Admin findByUsername(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public void updatePwd(String newPassword) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        LambdaUpdateWrapper<Admin> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Admin::getId, id);
        updateWrapper.set(Admin::getPassword, Md5Util.getMD5String(newPassword));
        adminMapper.update(updateWrapper);
    }

    @Override
    public void update(AdminUpdateDTO adminUpdateDTO) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminUpdateDTO, admin);
        adminMapper.updateById(admin);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        Admin admin = new Admin();
        admin.setAvatar(avatarUrl);
        admin.setId(id);
        adminMapper.updateById(admin);
    }

    @Override
    public List<Role> getRoleList(Long adminId) {
        return adminRoleRelationMapper.getRoleList(adminId);
    }

    @Override
    public void updateRole(Long adminId, List<Long> roleIds) {
        //先删除原来的关系
        LambdaQueryWrapper<AdminRoleRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminRoleRelation::getAdminId, adminId);
        adminRoleRelationMapper.delete(queryWrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<AdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
                adminRoleRelation.setAdminId(adminId);
                adminRoleRelation.setRoleId(roleId);
                list.add(adminRoleRelation);
            }
            adminRoleRelationMapper.insert(list);
        }
    }

    @Override
    public List<Menu> getMenuList() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        return roleMapper.getMenuList(id);
    }
}




