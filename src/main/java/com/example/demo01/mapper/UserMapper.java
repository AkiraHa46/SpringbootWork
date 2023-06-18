package com.example.demo01.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo01.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @Select("select * from user ")
//    List<User> findAll();
//
//    @Insert("INSERT into user (id,name,sex,mark,username,password)  VALUES (#{id},#{name},#{sex},#{mark},#{username},#{password})")
//    int insert (User student);
//
//    int update(User user);
//
//
//    @Delete("delete from user where id= #{id}")
//        Integer deleteByID(@Param("id") Integer id);
//
//    @Select("select * from user where name like #{name} limit #{pageNum},#{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize ,String name);
//
//    @Select("select count(*) from user  ")
//    Integer selectTotal();


}
