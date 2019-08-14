package com.hqumath.androidmvvm.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;


/**
 * ****************************************************************
 * 文件名称: StringUtilsTest
 * 作    者: Created by gyd
 * 创建时间: 2019/8/9 17:28
 * 文件描述: JUnit框架
 * 注意事项: https://blog.csdn.net/qq_17766199/article/details/78243176
 * 版权声明:
 * ****************************************************************
 */
public class StringUtilsTest {

    @Before
    public void beforeTest() {
        System.out.println("测试开始");
    }

    @After
    public void afterTest() {
        System.out.println("测试结束");
    }

    @Test
    public void getSizeString() {
        String msg = StringUtils.getSizeString(102400);
        assertEquals("100.00 KB", msg);

        assertThat("Checking that StringUtils.getSizeString", msg, is(equalTo("100.00 KB")));
    }

    @Test
    public void equals() {
        assertTrue(StringUtils.equals("test", "test"));
        //assertFalse(StringUtils.equals(null, "test"));

        assertThat("Checking that StringUtils.equals", StringUtils.equals("test", "test"), is(true));
    }
}