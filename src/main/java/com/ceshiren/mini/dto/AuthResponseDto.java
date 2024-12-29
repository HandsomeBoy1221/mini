/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.dto;

public class AuthResponseDto {
    private String username;
    private String accessToken;

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "username='" + username + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    public AuthResponseDto() {
    }

    public AuthResponseDto(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
