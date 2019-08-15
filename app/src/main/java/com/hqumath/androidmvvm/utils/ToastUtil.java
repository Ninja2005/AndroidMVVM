package com.hqumath.androidmvvm.utils;

import android.widget.Toast;

/**
 * ****************************************************************
 * 文件名称: ToastUtil
 * 作    者: Created by gyd
 * 创建时间: 2019/1/23 10:00
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ToastUtil {

    public static void toast(String s){
        Toast.makeText(Util.getContext(), s, Toast.LENGTH_SHORT).show();
    }

}
