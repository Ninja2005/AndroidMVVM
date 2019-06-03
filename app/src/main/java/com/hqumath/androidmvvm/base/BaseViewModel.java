package com.hqumath.androidmvvm.base;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

/**
 * ****************************************************************
 * 文件名称: BaseViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:14
 * 文件描述: 拥有生命周期感应，持有生命周期
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class BaseViewModel<M> extends AndroidViewModel implements LifecycleObserver {
    protected M model;
    //弱引用持有
    private WeakReference<LifecycleProvider> lifecycle;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public WeakReference<LifecycleProvider> getLifecycleProvider() {
        return lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
    }
}
