package com.ceshiren.mini.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "用户实体类",description = "请求参数的用户实体类")
public class UserDto {
    /**
     * 主键
     */
    private Integer id;


    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",example = "admin",required = true)

    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",example = "admin",required = true)

    private String password;

    /**
     * 邮箱
     */
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

