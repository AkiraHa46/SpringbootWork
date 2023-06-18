package com.example.demo01.service.impl;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/15
 * @Time 20:32
 * @Description
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo01.entity.Files;
import com.example.demo01.mapper.FileMapper;
import com.example.demo01.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements FileService {

    @Autowired
    private FileMapper fileMapper;


}
