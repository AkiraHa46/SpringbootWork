package com.example.demo01.service.impl;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:42
 * @Description
 */

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo01.entity.Menu;
import com.example.demo01.entity.Role;
import com.example.demo01.entity.RoleMenu;
import com.example.demo01.mapper.RoleMapper;
import com.example.demo01.mapper.RoleMenuMapper;
import com.example.demo01.service.MenuService;
import com.example.demo01.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuService menuService;

    @Override
    public int saveRole(Role role) {
        if (role.getId() == null) { //user没有id, 则表示新增
            return roleMapper.insert(role);
        }else {  //否则为更新
            return roleMapper.update(role);
        }
    }

//    @Override
//    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//
//    }

//    @Override
//    public List<Integer> getRoleMenu(Integer roleId) {
//        return null;
//    }

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_id", roleId);
//        roleMenuMapper.delete(queryWrapper);

//        先删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);

//        再把前端传过来的菜单id数组绑定到当前这个角色上去
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) { //二级菜单 并且传过来的menuId数组里面没有它的父级id
//                那么我们就得补上这个父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }


}
