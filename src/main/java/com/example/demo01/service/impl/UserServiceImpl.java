package com.example.demo01.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo01.common.Constanes;
import com.example.demo01.controller.dto.UserDTO;
import com.example.demo01.entity.Menu;
import com.example.demo01.entity.User;
import com.example.demo01.exception.ServiceException;
import com.example.demo01.mapper.RoleMapper;
import com.example.demo01.mapper.RoleMenuMapper;
import com.example.demo01.mapper.UserMapper;
import com.example.demo01.service.MenuService;
import com.example.demo01.service.UserService;
import com.example.demo01.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuService menuService;

    private static final Log LOG = Log.get();


    public boolean saveUser(User user) {
//        if (user.getId() ==null){
//            return save(user);
//        }else{
//            return updateById(user);
//        }
        return saveOrUpdate(user);
    }


//    登录校验
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one!=null){
//             one.setPassword(null);

            //设置token
             String token = TokenUtils.getToken(one.getId().toString(),one.getPassword());
             userDTO.setToken(token);
             one.setPassword(null);
             BeanUtil.copyProperties(one,userDTO,true); //使用hutool工具类浅拷贝给前端返回数据

            String role =one.getRole();//ROLE_ADMIN 类似标识
//            设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            System.err.println("roleMenus: "+roleMenus);
            userDTO.setMenus(roleMenus);
            return userDTO;
         }else {
           throw new ServiceException(Constanes.CODE_600,"用户名或者密码错误");
         }

        }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one==null){

            one =new User();
            BeanUtil.copyProperties(userDTO,one,true); //使用hutool工具类浅拷贝给前端返回数据
            save(one); //把copy完之后的用户对象储存到数据库中
        }else
        {
            throw new ServiceException(Constanes.CODE_600,"用户已经存在");
        }
        return null;
    }

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息

        }
        catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constanes.CODE_500,"系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
//            当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        System.err.println("menuIds:"+menuIds);
//            查出系统所有的菜单
        List<Menu> menus = menuService.findMenus("");
        System.err.println("menus:"+menus);
//            new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
//            筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
//                removeif 移除children 里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}

