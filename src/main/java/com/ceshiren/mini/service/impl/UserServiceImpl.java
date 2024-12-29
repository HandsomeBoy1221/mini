/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.service.impl;

import com.ceshiren.mini.converter.UserConverter;
import com.ceshiren.mini.dao.UserMapper;
import com.ceshiren.mini.dto.UserDto;
import com.ceshiren.mini.entity.User;
import com.ceshiren.mini.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    UserConverter userConverter;

    @Override
    public UserDto findByName(String uname) {
        User user = new User();
        user.setName(uname);
        User findByNameUser = userMapper.selectOne(user);
        UserDto userDto = userConverter.userForUserDto(findByNameUser);
        return userDto;
    }

    @Override
    public boolean insert(UserDto userDto) {
        //查找用户名是否注册
        User user1 = new User();
        user1.setName(userDto.getUsername());
        User findByUname = userMapper.selectOne(user1);
        //查找邮箱是否注册
        User user2 = new User();
        user2.setEmail(userDto.getEmail());
        User findByEmail = userMapper.selectOne(user2);

        if(null==findByUname && null==findByEmail){
            System.out.println("可以进行注册");
            User user = userConverter.userDtoForUser(userDto);
            user.setStatus(1);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            int i = userMapper.insert(user);
            return true;

        }else {
            System.out.println("数据库已经存在该用户");
            return false;
        }
    }
}
