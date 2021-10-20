package com.hqumath.androidmvvm.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.hqumath.androidmvvm.ui.main.MainActivity;
import com.hqumath.androidmvvm.utils.LogUtil;

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
public class CrashHandler implements UncaughtExceptionHandler {
    private Application application;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //单例模式-静态内部类
    private static class CrashHandlerHolder {
        private static final CrashHandler instance = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return CrashHandler.CrashHandlerHolder.instance;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     */
    public void init(Application application) {
        this.application = application;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LogUtil.e("error ;", e);
                //LogUtil.e(this.getClass().toString(), e.toString());
            }
            // 重启app
            Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
            //PendingIntent restartIntent = PendingIntent.getActivity(application.getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent restartIntent = PendingIntent.getActivity(application.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // 退出程序
            AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
            // 3秒后重启
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 3000, restartIntent);
            //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
            AppManager.getInstance().clear();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 自己处理异常
     *
     * @param ex
     * @return true 已处理；false 未处理
     */
    private boolean handleException(Throwable ex) {
        if (null == ex) {
            return false;
        }
        //使用Toast来显示异信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(application.getApplicationContext(), "检测到程序异常，即将退出", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        //collectDeviceInfo(MyApplication.getContext());
        // 保存日志文件
        //saveCrashInfo2File(ex);
        return true;
    }

    /**
     * @描述:收集设备参数信息
     */
    /*private void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfos.put("versionName", versionName);
                mInfos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }*/

    /**
     * @throws
     * @描述:保存错误信息到文件中 目录/sdcard/qfangadtv/crash/
     * @返回类型 void 返回文件名称,便于将文件传送到服务器
     */
    /*private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = mFormatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/×××/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }*/
}
