/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.security.jwt;

import com.ceshiren.mini.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//jwt token过滤器
@Component
public class JWTTokenFilter extends OncePerRequestFilter {
    @Resource
    JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //如果没有请求头Authorization信息，则直接跳转
        if(!hasAuthorizationHeader(request)){
            //跳转
            filterChain.doFilter(request,response);
            return;
        }
        //获取access token
        String accessToken = getAccessToken(request);
        //accesstoken解析异常
        if(!jwtUtils.validateAccessToken(accessToken)){
            //跳转
            filterChain.doFilter(request,response);
            return;
        }
        //token令牌验证 如果成功，鉴权通过 直接到业务逻辑请求
        setAuthorizationContext(accessToken,request);
        filterChain.doFilter(request,response);

    }
    private void setAuthorizationContext(String accessToken, HttpServletRequest request){
        User user = new User();
        String subject = jwtUtils.getSubject(accessToken);//1,admin
        String[] subArrays = subject.split(",");
        //subArrays[0] --- id   subArrays[1] ---- name
        user.setId(Integer.valueOf(subArrays[0]));
        user.setName(subArrays[1]);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);



    }


    //请求头token信息是否存在
    private boolean hasAuthorizationHeader(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        //如果不存在则为null
        if(ObjectUtils.isEmpty(authorization) || !authorization.startsWith("Bearer")){
            return false;
        }
        return true;
    }
    //获取accessToken
    private String getAccessToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        String token = authorization.split(" ")[1].trim();
        System.out.println("token:" + token);
        return token;
    }
}
