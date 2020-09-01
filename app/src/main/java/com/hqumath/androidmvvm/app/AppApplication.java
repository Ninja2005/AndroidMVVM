package com.hqumath.androidmvvm.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.hqumath.androidmvvm.BuildConfig;
import com.hqumath.androidmvvm.utils.Density;
import com.hqumath.androidmvvm.utils.LogUtil;
import com.hqumath.androidmvvm.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: AppApplication
 * 作    者: Created by gyd
 * 创建时间: 2019/5/31 17:04
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class AppApplication extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
        //初始化工具类
        CommonUtil.init(this);
        //是否开启打印日志
        LogUtil.init(BuildConfig.DEBUG);
        //异常捕获
        AppCrashHandler.getInstance().init();
        //屏幕适配方案，根据ui图修改,屏幕最小宽度375dp
        Density.setDensity(this, 375f);
    }

    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getInstance().removeActivity(activity);
            }
        });
    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }
}
