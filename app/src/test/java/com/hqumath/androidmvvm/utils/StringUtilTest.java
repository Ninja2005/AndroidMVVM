package com.hqumath.androidmvvm.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * ****************************************************************
 * 文件名称: StringUtilTest
 * 作    者: Created by gyd
 * 创建时间: 2019/8/9 17:28
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class StringUtilTest {

    @Test
    public void getSizeString() {
        String msg = StringUtil.getSizeString(102400);
        assertEquals("100.00 KB", msg);
    }
}