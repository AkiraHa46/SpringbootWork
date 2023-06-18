package com.example.demo01.controller;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:46
 * @Description
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo01.common.Result;
import com.example.demo01.entity.Role;
import com.example.demo01.mapper.RoleMapper;
import com.example.demo01.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleService roleService;

    //    新增和修改
    @PostMapping
    public Result saveOrUpdate(@RequestBody Role role) {
        //        新增或者更新
        if (roleService.saveRole(role) > 0){
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", role.getName());
            Role one = roleService.getOne(queryWrapper);
            return Result.success(one, "修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

//    @PostMapping
//    public Integer save(@RequestBody User user) {
//        return userMapper.insert(user);
//    }

    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //    查询所有数据
    @GetMapping("/findAll")
    List<Role> contextLoads() {
        List<Role> list = roleMapper.findAll();
//        for (Role role : list) {
//            System.out.println(role);
//        }
        return list;
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id) {
        return roleMapper.deleteById(id);
    }

    @PostMapping("/del/batch")
    public Integer deleteBatch(@RequestBody List<Integer> ids) {
        return roleMapper.deleteBatchIds(ids);
    }

    //    分页查询接口
//接口路径: /user/page
//    @RequestParam接口:  ?pageNum=1&pageSize=10
    @GetMapping("/findPage")
    Map<String, Object> findPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam String name) {
        pageNum = (pageNum-1)*pageSize;
        name = "%" + name + "%";
        List<Role> roles = roleMapper.selectPage(pageNum, pageSize, name);
        Integer total = roleMapper.selectTotal(name);
        HashMap<String, Object> data = new HashMap<>();
        data.put("data", roles);
        data.put("total", total);

//        获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println(currentUser.getNickname());
        return data ;
    }

    /**
     * 绑定角色和菜单的关系
     * @param roleId    角色id
     * @param menuIds   菜单id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable("roleId") Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable("roleId") Integer roleId) {
        return Result.success(roleService.getRoleMenu(roleId));
    }
}
