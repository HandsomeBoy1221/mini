/*
 * @Author: 霍格沃兹测试开发学社
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */

package com.ceshiren.mini.exception;

import com.ceshiren.mini.util.R;
import com.ceshiren.mini.util.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

import static com.ceshiren.mini.util.ResultCode.*;


//ControllerAdvice  AOP面向切面编程，对原来对功能没有侵入性，原来什么样子现在还是什么样子
//我只是在原来的功能的基础上给你扩展出一个功能，统一异常处理功能
//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    String tips = "系统繁忙，请稍后重试";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
//    @ResponseBody
    public R exceptionHandler(Exception e){
        //记录日志
        //通知运维
        //通知开发
        //控制台打印异常，万一出现异常调试
        e.printStackTrace();
        return R.error(INTERNAL_SERVER_ERROR).message("非业务异常 "+ tips);

    }


    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R servceExceptionHandler(HttpServletRequest req, ServiceException serviceException){
        //获取请求路径
        String requestUrl = req.getRequestURI();
        //获取请求方法 GET POST
        String method = req.getMethod();
        //命令行打印trace异常
        serviceException.printStackTrace();
        HashMap<String, Object> map = new HashMap<>(){{
            put("error api", method + " "+ requestUrl);
            put("error msg", serviceException.getMessage());
        }};
        //统一返回结果 5001, "系统繁忙，请稍后重试"
        return R.error(ResultCode.INTERNAL_SERVER_ERROR).data(map);
    }



    ////    .ArithmeticException
    @ExceptionHandler({ArithmeticException.class})
//    @ResponseBody
    public R exceptionHandler1(ArithmeticException e){
        e.printStackTrace();
        return R.error(INTERNAL_SERVER_ERROR).message("当前人数过多，请稍后再试");
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public R userExHandler(UsernameNotFoundException e){
        e.printStackTrace();
        return R.error(UNREGISTER);
    }
    @ExceptionHandler({BadCredentialsException.class})
    public R BadCredentialsHandler(BadCredentialsException e){
        e.printStackTrace();
        return R.error(PWDWRONG);
    }



}