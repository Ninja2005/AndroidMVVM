package com.hqumath.androidmvvm.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.hqumath.androidmvvm.utils.SPUtils;
import com.hqumath.androidmvvm.utils.Utils;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: MyRepository
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:29
 * 文件描述: MVVM的Model层，统一模块的数据仓库，可配合Room框架使用
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyRepository {
    private static volatile MyRepository INSTANCE = null;//禁止重排序
    private AppDatabase mDatabase;

    private MyRepository() {
        mDatabase = AppDatabase.getInstance(Utils.getContext());
    }

    public static MyRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (MyRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyRepository();
                }
            }
        }
        return INSTANCE;
    }

    public void runInTransaction(Runnable runnable){
        mDatabase.runInTransaction(runnable);
    }

    public void saveUserName(String userName) {
        SPUtils.getInstance().put("UserName", userName);
    }

    public String getUserName() {
        return SPUtils.getInstance().getString("UserName");
    }

    public void savePassword(String password) {
        SPUtils.getInstance().put("password", password);
    }

    public String getPassword() {
        return SPUtils.getInstance().getString("password");
    }

    public void saveToken(String token) {
        SPUtils.getInstance().put("token", token);
    }

    public String getToken() {
        return SPUtils.getInstance().getString("token");
    }

    //用户信息表操作
    public LiveData<List<UserInfoEntity>> loadAllUsers() {
        return mDatabase.userInfoDao().loadAll();
    }

    public List<UserInfoEntity> loadAllUsers1() {
        return mDatabase.userInfoDao().loadAll1();
    }

    public DataSource.Factory<Integer, UserInfoEntity> loadAllUsers2() {
        return mDatabase.userInfoDao().loadAll2();
    }

    public void insertAllUsers(List<UserInfoEntity> list) {
        mDatabase.userInfoDao().insertAll(list);
    }


    public void deleteAllUsers() {
        mDatabase.userInfoDao().deleteAll();
    }
}
