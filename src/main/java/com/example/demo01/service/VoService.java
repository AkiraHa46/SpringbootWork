package com.example.demo01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo01.entity.Vo;

public interface VoService extends IService<Vo> {


    public boolean saveVo(Vo vo);
}
