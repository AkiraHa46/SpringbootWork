package com.example.demo01.mapper;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/14
 * @Time 15:22
 * @Description
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo01.entity.Files;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<Files> {

    @Select("select * from t_file where name like #{name} and is_delete like false limit #{pageNum}, #{pageSize}")
    List<Files> selectPage(Integer pageNum, Integer pageSize, String name);

    @Select("select count(*) from t_file where name like #{name} and is_delete like false")
    Integer selectTotal(String username);

}
