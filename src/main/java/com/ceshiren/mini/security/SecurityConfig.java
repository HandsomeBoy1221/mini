/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.security;

import com.ceshiren.mini.security.jwt.JWTTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//Security框架的相关配置
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JWTTokenFilter jwtTokenFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //身份验证管理器
    @Resource
    CustomUserDetailService customUserDetailService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.userDetailsService(customUserDetailService);
        auth.authenticationProvider(daoAuthenticationProvider());

    }
    @Bean
    public AuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);//UserNotFoundExceptions
        return daoAuthenticationProvider;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_OK,authException.getMessage());
                })
        .and()
            .authorizeRequests()
//                .anyRequest().permitAll()//所有请求全部通过
                .antMatchers(HttpMethod.GET,
                        List.of(
                                        ApiPath.GetApi.values()).stream().map(postApi -> postApi.getPath()
                                )
                                .toArray(String[]::new)
                ).permitAll()
                .antMatchers(HttpMethod.POST,
                        List.of(
                                ApiPath.PostApi.values()).stream().map(postApi -> postApi.getPath()
                                )
                                .toArray(String[]::new)
                ).permitAll()
                .anyRequest().authenticated()
        .and()
                .addFilterAt(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

        ;

    }
}
