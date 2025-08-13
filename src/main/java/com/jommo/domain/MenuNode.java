package com.jommo.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuNode extends Menu {
    //子级菜单
    private List<MenuNode> children;
}
