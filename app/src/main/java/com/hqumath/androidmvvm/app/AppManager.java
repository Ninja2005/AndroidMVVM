package com.hqumath.androidmvvm.app;

import android.app.Activity;

import java.util.Stack;

/**
 * 栈管理
 * 添加、删除当前activity、删除指定的activity、清空栈、求栈大小
 */
public class AppManager {

    private static Stack<Activity> activityStack = new Stack<Activity>();

    private static AppManager appManager = null;

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    /**
     * 添加一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }


    /**
     * 删除指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (activityStack.get(i).getClass() == activity.getClass()) {
                activity.finish();
                activityStack.remove(activity);
                break;
            }
        }
    }

    /**
     * 删除指定activity
     *
     * @param clazz
     */
    public void removeActivity(Class<?> clazz) {
        Activity act = null;
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            act = activityStack.get(i);
            if (act.getClass() == clazz) {
                act.finish();
                activityStack.remove(act);
                break;
            }
        }
    }

    /**
     * 删除当前activity
     */
    public void removeCurrent() {
        Activity curActivity = getCurrent();
        if (curActivity != null) {
            curActivity.finish();
            activityStack.remove(curActivity);
        }
    }

    /**
     * 删除当前activity
     */
    public Activity getCurrent() {
        if (!activityStack.empty()) {
            return activityStack.lastElement();
        }
        return null;
    }

    /**
     * 清空栈
     */
    public void clear() {
        while (activityStack != null && !activityStack.empty()) {
            Activity curActivity = getCurrent();
            if (curActivity == null) {
                break;
            }
            curActivity.finish();
            activityStack.remove(curActivity);
        }
    }

    /**
     * 求栈大小
     *
     * @return
     */
    public int getSize() {
        return activityStack.size();
    }

    /**
     * 保存的Activity是否为空
     * 在Application里判断程序是否在后台运行
     */
    public static boolean isStackEmpty() {
        return activityStack == null || activityStack.size() <= 0;
    }

}
