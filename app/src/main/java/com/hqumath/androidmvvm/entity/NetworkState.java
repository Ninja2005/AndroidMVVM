package com.hqumath.androidmvvm.entity;

/**
 * ****************************************************************
 * 文件名称: NetworkState
 * 作    者: Created by gyd
 * 创建时间: 2019/8/1 9:21
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class NetworkState {

    public enum Status {
        LOADING,
        SUCCESS,
        FAILED
    }

    public static final NetworkState LOADED = new NetworkState(Status.SUCCESS, "Success");
    public static final NetworkState LOADING = new NetworkState(Status.LOADING, "Loading");

    private Status status;
    private String msg;

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
