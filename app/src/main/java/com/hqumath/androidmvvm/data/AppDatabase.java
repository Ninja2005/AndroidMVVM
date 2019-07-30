package com.hqumath.androidmvvm.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.hqumath.androidmvvm.data.dao.UserInfoDao;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

/**
 * ****************************************************************
 * 文件名称: AppDatabase
 * 作    者: Created by gyd
 * 创建时间: 2019/7/4 11:35
 * 文件描述: 数据库
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
@Database(entities = {UserInfoEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    public abstract UserInfoDao userInfoDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            //对该对象加锁，线程安全
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, "basic.db")
                            .fallbackToDestructiveMigration()//升级时丢弃原来表
                            .build();
                }
            }
        }
        return sInstance;
    }
}
