package com.example.demo01.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo01.entity.Vo;
import com.example.demo01.mapper.FileMapper;
import com.example.demo01.service.VoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Vo")
public class Vocontroller {

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;//操作字符串类型的数据 因为使用的一般为json数据流
//    public static final String FILES_KEY="FILES_FRONT_ALL";


    @Autowired
    VoService voService;

    @Autowired
    FileMapper fileMapper;



    //    新增或者更新
    @PostMapping("/post")
    @ResponseBody
    public boolean saveVo(@RequestBody Vo vo){
        return voService.saveOrUpdate(vo);
    }


    @GetMapping("/page")
    @ResponseBody
    public IPage<Vo> VofindPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam String  username)
    {
        IPage<Vo> page= new Page<>(pageNum,pageSize);
        QueryWrapper<Vo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        return voService.page(page,queryWrapper);
    }


////    redis缓存
//   @GetMapping("/front/all")
//    public Result frontAll(){
//       //1.从缓存获取数据
//       String jsonStr = stringRedisTemplate.opsForValue().get(FILES_KEY);
//       List<Files> files;
//       if(StrUtil.isBlank(jsonStr)){//2、判断取出来的json是否为空
//            files = fileMapper.selectList(null);//3从数据库取出数据之后
//           // 4缓存到redis
//           stringRedisTemplate.opsForValue().set(FILES_KEY,JSONUtil.toJsonStr(files));//存入字符串
//       }else {
//           //5从redis缓存中获取数据
//            files = JSONUtil.toBean(jsonStr, new TypeReference<List>() {
//           }, true);
//           //使用hutool工具类的值可以把json转成Java的一个class
//       }
//
//
//       return Result.success();
//   }





}
