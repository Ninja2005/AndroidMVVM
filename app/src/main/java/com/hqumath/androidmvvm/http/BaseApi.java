package com.hqumath.androidmvvm.http;

import com.trello.rxlifecycle2.LifecycleProvider;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * ****************************************************************
 * 文件名称: BaseApi
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 14:08
 * 文件描述:
 * 1.封装请求数据：Observable、HttpOnNextListener、activity
 * 2.检查返回值Function<T, R>
 * 3.观察请求状态 Observer<T>
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseApi<T> implements Function<BaseResultEntity<T>, T>, Observer<T> {
    //生命周期绑定
    private WeakReference<LifecycleProvider> lifecycle;
    /*回调*/
    protected SoftReference<HttpOnNextListener> listener;

    public BaseApi(HttpOnNextListener listener, WeakReference<LifecycleProvider> lifecycle) {
        this.listener = new SoftReference<>(listener);
        this.lifecycle = lifecycle;
    }

    public abstract Observable getObservable(Retrofit retrofit);

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle.get();
    }

    public HttpOnNextListener getHttpOnNextListener() {
        return listener.get();
    }

    @Override
    public T apply(BaseResultEntity<T> httpResult) {
        int res = httpResult.getRes();
        if (res == 0) {
            return httpResult.getData() == null ? (T) "{}" : httpResult.getData();//不能返回空值
        } else {
            throw new HandlerException.ResponseThrowable(httpResult.getMsg(), httpResult.getRes() + "");
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
        if (listener.get() != null) {
            listener.get().onSubscribe();
        }
    }

    @Override
    public void onNext(T t) {
        if (listener.get() != null) {
            listener.get().onNext(t);
        }
    }

    /**
     * 完成
     */
    @Override
    public void onComplete() {
        if (listener.get() != null) {
            listener.get().onComplete();
        }
    }

    /**
     * 对错误进行处理
     */
    @Override
    public void onError(Throwable e) {
        if (listener.get() != null) {
            listener.get().onError(HandlerException.handleException(e));
        }
    }

}
