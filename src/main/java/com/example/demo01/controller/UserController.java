package com.example.demo01.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo01.common.Result;
import com.example.demo01.entity.User;
import com.example.demo01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> index() {
        return userService.list();
    }


//    新增或者更新
     @PostMapping("/post")
     public boolean saveUser(@RequestBody User user){
        return userService.saveUser(user);
     }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

//    批量删除
    @PostMapping("/del/batch")  //[1,2,3]
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }

//    分页查询
//    查询分页
//    接口路径：/user/page?pageNum=1&pageSize=10
//    @RequestParam接受
//    limit第一个参数=（pageNum-1）*pageSize
//@GetMapping("/page")
//public Map<String,Object> findPage(@RequestParam Integer pageNum,
//                                   @RequestParam Integer pageSize,
//                                   @RequestParam String  name)
//{
//
//    pageNum = (pageNum -1) * pageSize;
//    name="%"+name+"%";
//    List<User> data =userMapper.selectPage(pageNum,pageSize,name);
//    Integer total = userMapper.selectTotal();
//    Map<String,Object> res=new HashMap<>();
//    res.put("data",data);
//    res.put("total",total);
//    return res;
//}
@GetMapping("/page")
public IPage<User> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam (defaultValue="")String  name)
{

    IPage<User> page= new Page<>(pageNum,pageSize);
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("name",name);
   return userService.page(page,queryWrapper);

}

//导出接口
@GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
    //从数据库查询出所有的数据
    List<User> list =userService.list();
//    新建ExcelWriter
    ExcelWriter writer= ExcelUtil.getWriter(true);
//    自定义别名
    writer.addHeaderAlias("id","用户id");
    writer.addHeaderAlias("name","姓名");
    writer.addHeaderAlias("username","用户名");
    writer.addHeaderAlias("mark","标记");
//    写出list里面的对象到excel
    writer.write(list,true);
//    设置浏览器响应的格式
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
    String fileName = URLEncoder.encode("用户信息","UTF-8");
    response.setHeader("Content-Disposition","attachment;filename="+ fileName +".xlsx");

    ServletOutputStream out= response.getOutputStream();
    writer.flush(out,true);
    out.close();
    writer.close();
}

//导入接口
@PostMapping("/import")
    public boolean imp(MultipartFile file)throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader=ExcelUtil.getReader(inputStream);

//        忽略表头中文（方法2）
//        List<List<Object>> list=reader.read(1);
//        List<User> users = CollUtil.newArrayList();
//        for(List<Object> row : list){
//            User user=new User();
//            user.setUsername(row.get(0).toString());
//            user.setUsername(row.get(1).toString());
//            user.setUsername(row.get(2).toString());
//            user.setUsername(row.get(4).toString());
//            users.add(user);
//        }
//        userService.saveBatch(users);
//        return true;


    //        设置别名这里和上面反着来
//    reader.addHeaderAlias("用户id","id");
    reader.addHeaderAlias("姓名","name");
    reader.addHeaderAlias("用户名","username");
    reader.addHeaderAlias("标记","mark");

    List<User> list=reader.readAll(User.class);
    userService.saveBatch(list);
    return true;

    }

}




