package com.example.demo01.mapper;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:40
 * @Description
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo01.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from sys_role")
    List<Role> findAll();

    @Insert("insert into sys_role(name, description) values (#{name}, #{description})")
    int insert(Role role);

    //    @Update("update sys_user set username = #{username}, password = #{password}, " +
//            "nickname = #{nickname}, email = #{email}, phone = #{phone}, address = #{address} where id = #{id}")
    int update(Role role);

    Integer deleteById(@Param("id") Integer id);


    @Select("select * from sys_role where name like #{name} limit #{pageNum}, #{pageSize}")
    List<Role> selectPage(Integer pageNum, Integer pageSize, String name);

    @Select("select count(*) from sys_role where name like #{name}")
    Integer selectTotal(String name);

    @Select("select id from sys_role where flag = #{flag}")
    Integer selectByFlag(@Param(value = "flag") String role);



}
