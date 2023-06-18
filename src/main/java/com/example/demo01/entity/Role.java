package com.example.demo01.entity;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:39
 * @Description
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "sys_role")
public class Role {

//    private static final long serialVersionUID = 1L;

//    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    /**
     * 唯一标识
     */
    @ApiModelProperty("唯一标识")
    private String flag;
}
