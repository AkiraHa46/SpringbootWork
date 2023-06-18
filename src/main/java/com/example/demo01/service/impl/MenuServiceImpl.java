package com.example.demo01.service.impl;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:42
 * @Description
 */

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo01.entity.Menu;
import com.example.demo01.mapper.MenuMapper;
import com.example.demo01.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public int saveMenu(Menu menu) {
        if (menu.getId() == null) { //user没有id, 则表示新增
            return menuMapper.insert(menu);
        }else {  //否则为更新
            return menuMapper.update(menu);
        }
    }

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.like("name", name);
        queryWrapper.orderByAsc("id");
        List<Menu> list = list(queryWrapper);
//        找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
//        找出一级菜单的子菜单
        for (Menu menu : parentNodes) {
//            筛选所有数据中pid=父级id的数据就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;  //返回一个list
    }
}
