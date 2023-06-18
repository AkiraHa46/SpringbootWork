package com.example.demo01.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;


@Data
@TableName( value ="user")
@ToString
public class User {

    @TableId(type = IdType.AUTO) // 自动主键
    private String id;

    private String name;

    private String sex;

    private String mark;

    private String password;

    private String username;

    @TableField(value = "avatar")
    private String avatar;

    private String nickname;

    private String role;

    private String vo;


}
