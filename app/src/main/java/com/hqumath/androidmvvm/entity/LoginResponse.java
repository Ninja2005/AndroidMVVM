package com.hqumath.androidmvvm.entity;

import com.hqumath.androidmvvm.http.BaseResultEntity;

/**
 * ****************************************************************
 * 文件名称: LoginResponse
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 17:16
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginResponse {
    private String token;//新接口令牌

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
