package com.example.demo01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo01.entity.Vo;
import com.example.demo01.mapper.VoMapper;
import com.example.demo01.service.VoService;
import org.springframework.stereotype.Service;


@Service
public class VoServiceImpl extends ServiceImpl <VoMapper,Vo>implements VoService {

//    @Autowired
//    VoMapper voMapper;

    @Override
    public boolean saveVo(Vo vo) {
        return saveOrUpdate(vo);
    }
}
