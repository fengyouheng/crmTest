package cn.wolfcode.crm.util;

import cn.wolfcode.crm.domain.SystemMenu;

import java.util.List;

public abstract class MenuUtil {
    public static void filterMenu(List<SystemMenu> menuList, List<Long> userMenuIds) {
        for (int i = menuList.size() - 1; i >= 0; i--) {
            if (!userMenuIds.contains(menuList.get(i).getId())) {
                menuList.remove(i);
            } else {
                List<SystemMenu> children = menuList.get(i).getChildren();
                if (children != null && children.size() > 0) {
                    filterMenu(children, userMenuIds);
                }
            }
        }
    }
}
