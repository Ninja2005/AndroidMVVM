package com.hqumath.androidmvvm.http;

import com.hqumath.androidmvvm.BuildConfig;
import com.hqumath.androidmvvm.utils.LogUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * ****************************************************************
 * 文件名称: RetrofitClient
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 14:20
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class RetrofitClient {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 6;

    private volatile static RetrofitClient INSTANCE;
    private final Retrofit retrofit;

    //获取单例
    public static RetrofitClient getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }

    //构造方法
    private RetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtil.i("RxRetrofit", "Retrofit====Message:" + message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));//打印的等级
        }
        /*创建retrofit对象*/
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppNetConfig.baseUrl)
                .build();
    }

    public void sendHttpRequest(BaseApi basePar) {
        /*rx处理*/
        basePar.getObservable(retrofit)
                /*生命周期管理*/
                .compose(basePar.getLifecycleProvider().bindToLifecycle())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断*/
                .map(basePar)
                .subscribe(basePar);
    }

    /**
     * 在IO线程观察
     * @param basePar
     */
    public void sendHttpRequestIO(BaseApi basePar) {
        /*rx处理*/
        basePar.getObservable(retrofit)
                /*结果判断*/
                .map(basePar)
                .subscribe(basePar);
    }
}
