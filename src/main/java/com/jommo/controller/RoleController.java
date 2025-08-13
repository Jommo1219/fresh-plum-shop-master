package com.jommo.controller;


import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Result;
import com.jommo.common.Status;
import com.jommo.domain.Menu;
import com.jommo.domain.Role;
import com.jommo.dto.RoleDTO;
import com.jommo.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    //添加
    @PostMapping
    public Result add(@RequestBody RoleDTO roleDTO) {
        Role existedRole = roleService.getByName(roleDTO.getName());
        if (existedRole != null) {
            return Result.error("该角色名称已存在");
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        roleService.save(role);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<Page<Role>> getList(String name, Integer current, Integer size) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(Role::getName, name);
        }
        Page<Role> page = roleService.page(new Page<>(current, size), queryWrapper);
        return Result.success(page);
    }

    //修改
    @PutMapping
    public Result update(@RequestBody @Validated RoleDTO roleDTO) {
        Role existedRole = roleService.getByName(roleDTO.getName());
        if (!Objects.isNull(existedRole) && !existedRole.getId().equals(roleDTO.getId())) {
            return Result.error("该角色名称已存在");
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        roleService.updateById(role);
        return Result.success();
    }

    //删除
    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        roleService.removeById(id);
        return Result.success();
    }

    //获取所有角色
    @GetMapping("/getAll")
    public Result<List<Role>> getAll() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, Status.ENABLED);
        return Result.success(roleService.list(queryWrapper));
    }

    //修改角色状态
    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestParam Long id) {
        Role role = roleService.getById(id);
        role.setStatus(Objects.equals(role.getStatus(), Status.ENABLED) ? Status.DISABLED : Status.ENABLED);
        roleService.updateById(role);
        return Result.success();
    }

    //分配菜单
    @PostMapping("/allocMenu")
    public Result allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        roleService.allocMenu(roleId, menuIds);
        return Result.success();
    }

    //获取角色相关菜单
    @GetMapping("/listMenu/{roleId}")
    @Transactional
    public Result<List<Menu>> listMenu(@PathVariable Long roleId) {
        List<Menu> menuList = roleService.listMenu(roleId);
        return Result.success(menuList);
    }

}
