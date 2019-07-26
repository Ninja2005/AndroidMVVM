package com.hqumath.androidmvvm.utils;

import java.util.Locale;

public class StringUtils {

    public static String getSizeString(long size){
        if(size < 1024){
            return String.format(Locale.getDefault(), "%d B", size);
        }else if(size < 1024 * 1024){
            float sizeK = size / 1024f;
            return String.format(Locale.getDefault(), "%.2f KB", sizeK);
        }else if(size < 1024 * 1024 * 1024){
            float sizeM = size / (1024f * 1024f);
            return String.format(Locale.getDefault(), "%.2f MB", sizeM);
        }else if(size / 1024 < 1024 * 1024 * 1024){
            float sizeG = size / (1024f * 1024f * 1024f);
            return String.format(Locale.getDefault(), "%.2f GB", sizeG);
        }
        return null;
    }

    /**
     * 参数可以为空
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return a.equals(b);
    }
}
