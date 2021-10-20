package com.hqumath.androidmvvm.repository;

import com.hqumath.androidmvvm.app.Constant;
import com.hqumath.androidmvvm.base.BaseModel;
import com.hqumath.androidmvvm.net.HttpListener;
import com.hqumath.androidmvvm.net.RetrofitClient;
import com.hqumath.androidmvvm.utils.SPUtil;

/**
 * ****************************************************************
 * 文件名称: MyModel
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 数据层，数据的获取、存储和处理 https://juejin.cn/post/6844903812658888712
 * 注意事项:
 * 1.提供的数据业务层直接可用
 * 2.相同的接口不要多次实现
 * 3.方便自动化测试
 * ****************************************************************
 */
public class MyModel extends BaseModel {
    //模拟登陆接口
    public void login(String userName, String passWord, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getUserInfo(userName), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                //数据校验、处理
                SPUtil.getInstance().put(Constant.USER_NAME, userName);
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getUserInfo(String userName, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getUserInfo(userName), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getFollowers(String userName, int pageSize, long pageIndex, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getFollowers(userName, pageSize, pageIndex), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getMyRepos(String userName, int pageSize, long pageIndex, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getMyRepos(userName, pageSize, pageIndex), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }

    public void getStarred(String userName, int pageSize, long pageIndex, HttpListener listener) {
        sendRequest(RetrofitClient.getInstance().getApiService().getStarred(userName, pageSize, pageIndex), new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                listener.onSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                listener.onError(errorMsg, code);
            }
        });
    }
}
