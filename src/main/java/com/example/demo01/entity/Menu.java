package com.example.demo01.entity;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 10:21
 * @Description
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@TableName(value = "sys_menu")
@ToString
public class Menu {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;
    private String path;
    private String icon;
    private String description;
    @TableField(exist = false)
    private List<Menu> children;
    @TableField(value = "pid")
    private Integer pid;


    private String pagePath;
}
