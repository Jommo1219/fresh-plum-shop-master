package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Result;
import com.jommo.common.Status;
import com.jommo.domain.Admin;
import com.jommo.domain.Menu;
import com.jommo.domain.Role;
import com.jommo.dto.*;
import com.jommo.service.AdminService;
import com.jommo.util.JwtUtil;
import com.jommo.util.Md5Util;
import com.jommo.util.ThreadLocalUtil;
import io.micrometer.common.util.StringUtils;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody @Validated AdminLoginDTO adminLoginDTO) {
        //根据用户名查询用户
        Admin loginAdmin = adminService.findByUsername(adminLoginDTO.getUsername());
        //判断该用户是否存在
        if (loginAdmin == null) {
            return Result.error("用户名或密码错误");
        }
        if (Objects.equals(loginAdmin.getStatus(), Status.DISABLED)) {
            return Result.error("该账号已被锁定，请联系管理员！");
        }
        //判断密码是否正确
        if (Md5Util.checkPassword(adminLoginDTO.getPassword(), loginAdmin.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginAdmin.getId());
            claims.put("username", loginAdmin.getUsername());
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 8, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }

    //获取用户详细信息
    @GetMapping("/info")
    public Result<Admin> info() {
        //根据用户名查询用户
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Admin admin = adminService.findByUsername(username);
        return Result.success(admin);
    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody @Validated AdminUpdatePwdDTO adminUpdatePwdDTO) {
        //校验参数
        if (!adminUpdatePwdDTO.getConfirmPassword().equals(adminUpdatePwdDTO.getNewPassword())) {
            return Result.error("两次填写的新密码不一样");
        }
        //原密码是否填写正确
        //调用service根据用户名拿到原密码，再与oldPwd比对
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Admin loginAdmin = adminService.findByUsername(username);
        if (!Md5Util.checkPassword(adminUpdatePwdDTO.getOldPassword(), loginAdmin.getPassword())) {
            return Result.error("原密码填写不正确");
        }
        //调用service完成密码更新
        adminService.updatePwd(adminUpdatePwdDTO.getNewPassword());
        return Result.success();
    }

    //更新用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated AdminUpdateDTO adminUpdateDTO) {
        if (!StringUtils.isEmpty(adminUpdateDTO.getUsername())) {
            adminService.update(adminUpdateDTO);
        } else {
            Admin existedAdmin = adminService.findByUsername(adminUpdateDTO.getUsername());
            if (Objects.nonNull(existedAdmin)) {
                return Result.error("该用户名已存在");
            }
            adminService.update(adminUpdateDTO);
        }
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        adminService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //分页查询后台用户
    @GetMapping("/list")
    public Result<Page<Admin>> list(Integer current, Integer size, String keyword) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Admin::getId, id);
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.and(wrapper ->
                    wrapper.like(Admin::getUsername, keyword)
                            .or()
                            .like(Admin::getNickname, keyword)
            );
        }
        queryWrapper.and(wrapper -> wrapper.like(Admin::getUsername, keyword).or().like(Admin::getNickname, keyword));
        Page<Admin> page = adminService.page(new Page<>(current, size), queryWrapper);
        return Result.success(page);
    }

    //添加用户
    @PostMapping
    public Result add(@RequestBody @Validated AdminAddDTO adminAddDTO) {
        Admin existedAdmin = adminService.findByUsername(adminAddDTO.getUsername());
        if (!Objects.isNull(existedAdmin)) {
            Result.error("该用户名已存在");
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddDTO, admin);
        admin.setPassword(Md5Util.getMD5String(adminAddDTO.getPassword()));
        adminService.save(admin);
        return Result.success();
    }

    //删除用户
    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        adminService.removeById(id);
        return Result.success();
    }

    //获取角色
    @GetMapping("/role/{adminId}")
    public Result<List<Role>> getRoleList(@PathVariable Long adminId) {
        List<Role> roleList = adminService.getRoleList(adminId);
        return Result.success(roleList);
    }

    //分配角色
    @PostMapping("/role/update")
    @Transactional
    public Result updateRole(@RequestParam("adminId") Long adminId,
                             @RequestParam("roleIds") List<Long> roleIds) {
        adminService.updateRole(adminId, roleIds);
        return Result.success();
    }

    //修改用户状态
    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestParam Long id) {
        Admin admin = adminService.getById(id);
        admin.setStatus(Objects.equals(admin.getStatus(), Status.ENABLED) ? Status.DISABLED : Status.ENABLED);
        adminService.updateById(admin);
        return Result.success();
    }

    //根据当前用户的角色获取菜单列表
    @GetMapping("/listMenu")
    public Result<List<Menu>> getMenuList() {
        List<Menu> list = adminService.getMenuList();
        return Result.success(list);
    }
}
