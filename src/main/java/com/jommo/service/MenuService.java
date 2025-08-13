package com.jommo.service;

import com.jommo.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jommo.domain.MenuNode;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【menu(后台菜单表)】的数据库操作Service
* @createDate 2025-05-07 16:48:51
*/
public interface MenuService extends IService<Menu> {

    //树形结构获取菜单列表
    List<MenuNode> treeList();
}
