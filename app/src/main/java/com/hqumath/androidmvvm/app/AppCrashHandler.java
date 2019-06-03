package com.hqumath.androidmvvm.app;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import com.hqumath.androidmvvm.utils.LogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * ****************************************************************
 * 文件名称: AppCrashHandler
 * 作    者: Created by gyd
 * 创建时间: 2019/3/20 11:35
 * 文件描述: 异常拦截
 * 注意事项: UncaughtExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的。 如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 * 实现该接口并注册为程序中的默认未捕获异常处理 这样当未捕获异常发生时，就可以做些异常处理操作 例如：收集异常信息，发送错误报告 等。
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 * 版权声明:
 * ****************************************************************
 */
public class AppCrashHandler implements UncaughtExceptionHandler {
    /**
     * CrashHandler实例
     */
    private static AppCrashHandler INSTANCE;

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static AppCrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AppCrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     */
    public void init() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //主线程异常拦截
                while (true) {
                    try {
                        Looper.loop();//主线程的异常会从这里抛出
                    } catch (Throwable e) {
                        uncaughtException(null, e);
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            Activity current = AppManager.getInstance().currentActivity();
            if (current != null)
                current.finish();
            String mErrorMessage = getStackTraceString(ex);
            //只是打印出异常即可
            LogUtil.e(mErrorMessage);
        } catch (Exception e) {
            LogUtil.e(getStackTraceString(e));
        }
    }

    private String getStackTraceString(Throwable var0) {
        if (var0 != null) {
            StringWriter var1 = new StringWriter();
            PrintWriter var2 = new PrintWriter(var1);
            var0.printStackTrace(var2);
            return var1.toString();
        } else {
            return "";
        }
    }
}
