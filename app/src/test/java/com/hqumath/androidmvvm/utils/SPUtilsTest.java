package com.hqumath.androidmvvm.utils;

import android.content.SharedPreferences;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * ****************************************************************
 * 文件名称: SPUtilsTest
 * 作    者: Created by gyd
 * 创建时间: 2019/8/14 16:05
 * 文件描述: Mockito框架
 * 注意事项: https://blog.csdn.net/qq_17766199/article/details/78450007
 * 版权声明:
 * ****************************************************************
 */
@RunWith(MockitoJUnitRunner.class)
public class SPUtilsTest {
    private static final String KEY_NAME = "key_name";

    @Mock
    SharedPreferences sp;

    @Mock
    SharedPreferences.Editor editor;

    @Before
    public void beforeTest() {
        when(editor.putString(eq(KEY_NAME), anyString())).thenReturn(editor);
        when(editor.commit()).thenReturn(true);

        when(sp.edit()).thenReturn(editor);
        when(sp.getString(eq(KEY_NAME), anyString())).thenReturn("xiaoming");
        when(sp.getBoolean(eq(KEY_NAME), anyBoolean())).thenThrow(new NullPointerException("性别不明"));
    }

    @Test
    public void put() {
        boolean success = sp.edit().putString(KEY_NAME, "xiaoming").commit();
        assertTrue(success);
    }

    @Test
    public void getString() {
        assertEquals("xiaoming", sp.getString(KEY_NAME, ""));
    }

    @Test
    public void test(){
        System.out.println(sp.getString(KEY_NAME, ""));
//        System.out.println(sp.getBoolean(KEY_NAME, true));

        verify(sp).getString(KEY_NAME, "");
    }




}