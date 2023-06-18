package com.example.demo01.entity;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/23
 * @Time 20:07
 * @Description
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_role_menu")
@Data
public class RoleMenu {
    private Integer roleId;
    private Integer menuId;

}
