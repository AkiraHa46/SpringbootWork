package com.example.demo01.controller;


import cn.hutool.core.util.StrUtil;
import com.example.demo01.common.Constanes;
import com.example.demo01.common.Result;
import com.example.demo01.controller.dto.UserDTO;
import com.example.demo01.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

        /**
        * 登录功能
        */

        //    RequestBody 把前端传来的json转为对象
        @PostMapping
        private Result login(@RequestBody UserDTO userDTO){
        //        对userDTO进行校验
        String username =userDTO.getUsername();
        String password = userDTO.getPassword();

        if(StrUtil.isBlank(username)|| StrUtil.isBlank(password)){
        return Result.error(Constanes.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        
        return Result.success(dto);
        }

    /**
     * 注册功能
     */
     @PostMapping("/register")
      public Result register(@RequestBody UserDTO userDTO)
     {
         String username =userDTO.getUsername();
         String password = userDTO.getPassword();

         if(StrUtil.isBlank(username)|| StrUtil.isBlank(password)){
             return Result.error(Constanes.CODE_400,"参数错误");
         }
      return Result.success(userService.register(userDTO));
     }


}
