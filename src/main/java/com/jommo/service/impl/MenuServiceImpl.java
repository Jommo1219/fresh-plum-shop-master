package com.jommo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.Menu;
import com.jommo.domain.MenuNode;
import com.jommo.service.MenuService;
import com.jommo.mapper.MenuMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 不会开发的小虾米
 * @description 针对表【menu(后台菜单表)】的数据库操作Service实现
 * @createDate 2025-05-07 16:48:51
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuNode> treeList() {
        List<Menu> menuList = menuMapper.selectList(null);
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> convertMenuNode(menu, menuList))
                .collect(Collectors.toList());
    }

    //将menu转换为menuNode并设置children属性
    private MenuNode convertMenuNode(Menu menu, List<Menu> menuList) {
        MenuNode menuNode = new MenuNode();
        BeanUtils.copyProperties(menu, menuNode);
        List<MenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> convertMenuNode(subMenu, menuList)).toList();
        menuNode.setChildren(children);
        return menuNode;
    }
}




