package com.hqumath.androidmvvm.entity;

/**
 * ****************************************************************
 * 文件名称: NetworkState
 * 作    者: Created by gyd
 * 创建时间: 2019/6/28 16:05
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class NetworkState {
    private Status status;
    private String msg;

    public NetworkState(Status status, String msg){
        this.status =status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public enum Status{
        LOADING,
        SUCCESS,
        FAILED
    }
}
