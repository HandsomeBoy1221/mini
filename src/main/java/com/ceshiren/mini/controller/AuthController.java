/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.controller;

import com.ceshiren.mini.dto.AuthRegisterDto;
import com.ceshiren.mini.dto.AuthRequestDto;
import com.ceshiren.mini.dto.AuthResponseDto;
import com.ceshiren.mini.dto.UserDto;
import com.ceshiren.mini.security.jwt.JWTUtils;
import com.ceshiren.mini.service.UserService;
import com.ceshiren.mini.util.R;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.ceshiren.mini.util.ResultCode.UNREGISTER;

@RestController
public class AuthController {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    UserService userService;
    @Resource
    JWTUtils jwtUtils;
    @PostMapping("auth/login")
    public R login(@RequestBody AuthRequestDto authRequestDto){
//        try {
            //security框架操作 身份认证对象
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword())
            );
            User user = (User)authenticate.getPrincipal();

            UserDto userDto = userService.findByName(user.getUsername());
//            String accessToken = "JWT Token";
            String accessToken = jwtUtils.generateAccessToken(userDto);

            AuthResponseDto authResponseDto = new AuthResponseDto(userDto.getUsername(), accessToken);


            return R.ok().data(authResponseDto).message("登录成功");

//        }catch (BadCredentialsException e){
//            return R.error().code(PWDWRONG).message("用户密码错误");
//        }
//        catch (UsernameNotFoundException e){
//            return R.error().code(UNREGISTER).message("用户未注册");
//        }


    }

    @PostMapping("auth/register")
    public R register(@RequestBody AuthRegisterDto authRegisterDto){
        System.out.println("注册接口："+authRegisterDto);
        //密码的加密
        //1,创建解析器
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //密码加密
        String pwdEncode = bCryptPasswordEncoder.encode(authRegisterDto.getPassword());
        //UserService   UserDto
        UserDto userDto = new UserDto();
        userDto.setUsername(authRegisterDto.getUsername());
        userDto.setPassword(pwdEncode);
        userDto.setEmail(authRegisterDto.getEmail());
        return userService.insert(userDto)? R.ok().message("注册成功") :
                R.error(UNREGISTER).message("用户注册失败");


    }

}
