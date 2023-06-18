package com.example.demo01.mapper;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 9:40
 * @Description
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo01.entity.Menu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select * from sys_menu")
    List<Menu> findAll();

    @Insert("insert into sys_menu(name, path, icon, description, pid) " +
            "values (#{name}, #{path}, #{icon}, #{description}, #{pid}  )")
    int insert(Menu menu);

    //    @Update("update sys_user set username = #{username}, password = #{password}, " +
//            "nickname = #{nickname}, email = #{email}, phone = #{phone}, address = #{address} where id = #{id}")
    int update(Menu menu);

    Integer deleteById(@Param("id") Integer id);


    @Select("select * from sys_menu where name like #{name} limit #{pageNum}, #{pageSize}")
    List<Menu> selectPage(Integer pageNum, Integer pageSize, String name);

    @Select("select count(*) from sys_menu where name like #{name}")
    Integer selectTotal(String name);
}
