package com.hqumath.androidmvvm.http;

/**
 * 网络请求统一返回格式
 */
public class BaseResultEntity<T> {
    private int res;//0成功
    private String msg;
    private T data;
    private int count;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
