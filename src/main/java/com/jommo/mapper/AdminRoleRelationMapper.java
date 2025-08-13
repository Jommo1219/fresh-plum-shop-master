package com.jommo.mapper;

import com.jommo.domain.AdminRoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jommo.domain.Role;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【admin_role_relation(后台用户和角色关系表)】的数据库操作Mapper
* @createDate 2025-04-24 23:03:21
* @Entity com.jommo.domain.AdminRoleRelation
*/
public interface AdminRoleRelationMapper extends BaseMapper<AdminRoleRelation> {

    List<Role> getRoleList(Long adminId);
}




