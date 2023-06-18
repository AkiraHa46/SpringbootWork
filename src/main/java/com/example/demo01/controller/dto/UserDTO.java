package com.example.demo01.controller.dto;


import com.example.demo01.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 接受前端请求的参数
 */


@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String vo;
    private String token;
    private List<Menu> menus;
}
