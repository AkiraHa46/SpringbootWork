package com.example.demo01.controller;

import cn.hutool.core.collection.CollUtil;
import com.example.demo01.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {



    @GetMapping("example")
    public Result get(){
        Map<String,Object> map =new HashMap<>();
        map.put("x", CollUtil.newArrayList("星期一","星期二","星期三","星期四","星期五","星期六","星期天"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));

        return   Result.success(map);
    }



}
