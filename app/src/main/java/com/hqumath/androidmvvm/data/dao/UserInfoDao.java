package com.hqumath.androidmvvm.data.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: UserInfoDao
 * 作    者: Created by gyd
 * 创建时间: 2019/7/4 11:35
 * 文件描述: 用户信息表操作
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
@Dao
public interface UserInfoDao {
    @Query("select * from user_info")
    LiveData<List<UserInfoEntity>> loadAll();

    @Query("select * from user_info")
    List<UserInfoEntity> loadAll1();

    @Query("SELECT * FROM user_info ORDER BY indexInResponse ASC")
    DataSource.Factory<Integer, UserInfoEntity> loadAll2();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserInfoEntity> entity);

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateAll(List<UserInfoEntity> entity);
//
//    @Delete
//    void deleteAll(List<UserInfoEntity> entity);

    @Query("delete from user_info")
    void deleteAll();

    @Query("SELECT MAX(indexInResponse) + 1 FROM user_info")
    int getNextIndex();
}
