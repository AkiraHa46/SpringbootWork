package com.example.demo01.service;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:40
 * @Description
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo01.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    public int saveMenu(Menu menu);

    List<Menu> findMenus(String name);
}
