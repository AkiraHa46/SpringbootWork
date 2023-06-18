package com.example.demo01.service;//package com.example.demo03.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo01.controller.dto.UserDTO;
import com.example.demo01.entity.User;


public interface UserService extends IService<User>{

    boolean saveUser(User user);

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);


//    public boolean saveUser(User user) {
////        if (user.getId() ==null){
////            return save(user);
////        }else{
////            return updateById(user);
////        }
//        return saveOrUpdate(user);
//    }


}
