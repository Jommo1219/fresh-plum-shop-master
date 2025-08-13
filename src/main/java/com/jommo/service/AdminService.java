package com.jommo.service;

import com.jommo.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jommo.domain.Menu;
import com.jommo.domain.Role;
import com.jommo.dto.AdminUpdateDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【admin(管理员表)】的数据库操作Service
* @createDate 2025-04-23 15:28:37
*/
public interface AdminService extends IService<Admin> {
    //根据用户名查询后台用户
    Admin findByUsername(@NotEmpty(message = "用户名不能为空") String username);

    //修改密码
    void updatePwd(@NotEmpty(message = "请输入新密码") @Size(min = 8, max = 16, message = "密码长度需在8-16个字符之间") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "新密码必须包含大小写字母、数字和特殊字符") String newPassword);

    //修改用户基本信息
    void update(AdminUpdateDTO adminUpdateDTO);

    //修改用户头像
    void updateAvatar(@URL String avatarUrl);

    //根据后台用户id获取角色
    List<Role> getRoleList(Long adminId);

    //保存分配的角色
    void updateRole(Long adminId, List<Long> roleIds);

    //获取当前用户角色的菜单
    List<Menu> getMenuList();
}
