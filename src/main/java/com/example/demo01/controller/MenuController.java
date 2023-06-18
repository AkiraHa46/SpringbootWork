package com.example.demo01.controller;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:46
 * @Description
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo01.common.Constanes;
import com.example.demo01.common.Result;
import com.example.demo01.entity.Dict;
import com.example.demo01.entity.Menu;
import com.example.demo01.mapper.DictMapper;
import com.example.demo01.mapper.MenuMapper;
import com.example.demo01.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private MenuService menuService;

    @Resource
    private DictMapper dictMapper;

    //    新增和修改
    @PostMapping
    public Result saveOrUpdate(@RequestBody Menu menu) {
//                新增或者更新
        System.out.println("menu数据："+menu.toString());
        if (menuService.saveMenu(menu) > 0){
//            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("name", menu.getName());
//            Menu one = menuService.getOne(queryWrapper);
//            return Result.success(one, "修改成功");
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
//        if (menuService.saveOrUpdate(menu)) {
//            return Result.success("修改成功");
//        }else {
//            return Result.error("修改失败");
//        }
    }

//    @PostMapping
//    public Integer save(@RequestBody User user) {
//        return userMapper.insert(user);
//    }

    //    查询所有数据
    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(menuService.findMenus(name));
    }

    @GetMapping("/ids")
    public Result findAllIds() {
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id) {
        return menuMapper.deleteById(id);
    }

    @PostMapping("/del/batch")
    public Integer deleteBatch(@RequestBody List<Integer> ids) {
        return menuMapper.deleteBatchIds(ids);
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
        List<Menu> menus = menuMapper.selectPage(pageNum, pageSize, name);
        Integer total = menuMapper.selectTotal(name);
        HashMap<String, Object> data = new HashMap<>();
        data.put("data", menus);
        data.put("total", total);

//        获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println(currentUser.getNickname());
        return data ;
    }



    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constanes.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }
}
