package com.example.demo01.mapper;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/22
 * @Time 19:10
 * @Description
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo01.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
