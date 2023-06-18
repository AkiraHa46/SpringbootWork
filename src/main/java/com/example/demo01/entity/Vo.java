package com.example.demo01.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("vo")
public class Vo {

   @TableId(type = IdType.AUTO)
   private Integer id;
   private String  usern ame;
   private Integer phone;
   private String  vname;
}
