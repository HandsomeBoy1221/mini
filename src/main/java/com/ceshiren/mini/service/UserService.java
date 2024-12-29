/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.service;

import com.ceshiren.mini.dto.UserDto;

public interface UserService {
    UserDto findByName(String uname);

    boolean insert(UserDto userDto);
}
