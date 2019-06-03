package com.hqumath.androidmvvm.http;

import android.text.TextUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

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
    private WeakReference<LifecycleProvider> lifecycleProvider;
    /*回调*/
    private WeakReference<HttpOnNextListener> listener;

    public BaseApi(HttpOnNextListener listener, LifecycleProvider lifecycleProvider) {
        this.listener = new WeakReference(listener);
        this.lifecycleProvider = new WeakReference(lifecycleProvider);
    }

    public abstract Observable getObservable(Retrofit retrofit);

    public LifecycleProvider getLifecycleProvider() {
        return lifecycleProvider.get();
    }

    @Override
    public T apply(BaseResultEntity<T> httpResult) {
        Boolean res = httpResult.getRes();
//        if (TextUtils.isEmpty(type) || !type.equals(AppNetConfig.SUCCESS)) {
//            String resultCode = httpResult.getResultCode();
//            String resultMsg = httpResult.getResultMsg();
//            //处理特殊错误号
//            switch (resultCode) {
//                case HandleMessageCode.HMC_LOGIN:
//                    throw new HandlerException.ResponseThrowable("请先登录", resultCode);
//                case HandleMessageCode.HMC_LOGIN_OUT:
//                    throw new HandlerException.ResponseThrowable("您的账户已在其他设备登录,请重新登陆！", resultCode);
//                default:
//                    throw new HandlerException.ResponseThrowable(resultMsg, resultCode);
//            }
//        }
        return httpResult.getData();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
        if (listener.get() != null) {
            listener.get().onStart();
        }
        //showProgressDialog
    }

    /**
     * 完成
     */
    @Override
    public void onComplete() {
        if (listener.get() != null) {
            listener.get().onComplete();
        }
        //dismissProgressDialog();
    }

    /**
     * 对错误进行处理
     */
    @Override
    public void onError(Throwable e) {
        if (listener.get() != null) {
            listener.get().onError(HandlerException.handleException(e));
        }
        //dismissProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (listener.get() != null) {
            listener.get().onNext(t);
        }
    }
}
