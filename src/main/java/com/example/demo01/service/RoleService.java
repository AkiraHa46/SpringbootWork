package com.example.demo01.service;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:40
 * @Description
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo01.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    int saveRole(Role role);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
