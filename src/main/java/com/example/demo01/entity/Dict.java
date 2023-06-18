package com.example.demo01.entity;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 19:11
 * @Description
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_dict")
@Data
public class Dict {
    private String name;
    private String value;
    private String type;

}
