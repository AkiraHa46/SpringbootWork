package com.example.demo01.controller;
/*
 * Created with IntelliJ IDEA.
 * @Author 57406
 * @date 2023/4/14
 * @Time 14:49
 * @Description  文件上传相关接口
 */


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo01.common.Result;
import com.example.demo01.entity.Files;
import com.example.demo01.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;//操作字符串类型的数据 因为使用的一般为json数据流
    public static final String FILES_KEY="FILES_FRONT_ALL";



    @Autowired
    FileMapper fileMapper;

    /**
     * 文件上传接口
     * @param file 前端传过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size =file.getSize();
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断配置的文件目录是否存在，若不存在则创建一个新的目录
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        //定义文件唯一的一个标识码
        String uuid = IdUtil.fastSimpleUUID();
        File uploadFile = new File(fileUploadPath + uuid + StrUtil.DOT +type    );
        //把获取到的文件存储到磁盘目录里面去
        file.transferTo(uploadFile);
        //存储数据库
        return "";

    }

//    @GetMapping("/front/all")
//    public Result frontAll()
//    {
//      return   Result.success(fileMapper.selectList(null));
//    }

    //    redis缓存
    @GetMapping("/front/all")
    public Result frontAll(){
        //1.从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(FILES_KEY);
        List<Files> files;
        if(StrUtil.isBlank(jsonStr)){//2、判断取出来的json是否为空
            files = fileMapper.selectList(null);//3从数据库取出数据之后
            // 4缓存到redis
            stringRedisTemplate.opsForValue().set(FILES_KEY, JSONUtil.toJsonStr(files));//存入字符串
        }else {
            //5从redis缓存中获取数据
            files = JSONUtil.toBean(jsonStr, new TypeReference<List>() {
            }, true);
            //使用hutool工具类的值可以把json转成Java的一个class
        }
        return Result.success(files);
    }

}
