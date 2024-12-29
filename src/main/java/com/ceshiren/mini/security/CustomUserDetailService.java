/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.security;

import com.ceshiren.mini.dto.UserDto;
import com.ceshiren.mini.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByName(username);
        System.out.println("user:"+user);

        if(null == user){
            System.out.println("未查找到该用户");
            throw new UsernameNotFoundException("未查找到该用户：" + username);
        }
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        //String username, String password, boolean enabled, boolean accountNonExpired,
        //			boolean credentialsNonExpired, boolean accountNonLocked,
        //			Collection<? extends GrantedAuthority> authorities) {
        UserDetails userDetails = new User(
                username,
                "{bcrypt}" + user.getPassword(),//{noop}不加密  {bcrypt}加密
                true,
                true,
                true,
                true,
                authorities
                );
        return userDetails;

    }
}
