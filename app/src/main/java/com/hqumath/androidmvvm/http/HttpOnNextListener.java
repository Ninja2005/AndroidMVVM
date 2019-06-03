package com.hqumath.androidmvvm.http;


import io.reactivex.Observable;

/**
 * 成功回调处理
 */
public abstract class HttpOnNextListener<T> {

    /**
     * 开始
     */
    public abstract void onSubscribe();

    /**
     * 成功后回调方法
     * @param t
     */
    public abstract void onNext(T t);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     * @param e
     */
    public abstract void onError(HandlerException.ResponseThrowable e);

    /**
     * 完成
     */
    public abstract void onComplete();
}
