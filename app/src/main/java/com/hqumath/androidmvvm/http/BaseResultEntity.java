package com.hqumath.androidmvvm.http;

/**
 * 网络请求统一返回格式
 */
public class BaseResultEntity<T> {
    //判断标识
    private boolean res;
    //错误码
    private String errcode;
    //提示信息
    private String errmsg;
    //显示数据（用户需要关心的数据）
    private T data;

    public boolean getRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
