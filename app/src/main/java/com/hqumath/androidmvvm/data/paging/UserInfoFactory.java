package com.hqumath.androidmvvm.data.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.hqumath.androidmvvm.entity.UserInfoEntity;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

/**
 * ****************************************************************
 * 文件名称: UserInfoFactory
 * 作    者: Created by gyd
 * 创建时间: 2019/7/31 15:32
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class UserInfoFactory extends DataSource.Factory<Long, UserInfoEntity> {

    private MutableLiveData<UserInfoSource> sourceLiveData = new MutableLiveData<>();
    private WeakReference<LifecycleProvider> lifecycle;

    public UserInfoFactory(WeakReference<LifecycleProvider> lifecycle) {
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public DataSource<Long, UserInfoEntity> create() {
        UserInfoSource commitSource = new UserInfoSource(lifecycle);
        sourceLiveData.postValue(commitSource);
        return commitSource;
    }

    public MutableLiveData<UserInfoSource> getSourceLiveData() {
        return sourceLiveData;
    }
}
