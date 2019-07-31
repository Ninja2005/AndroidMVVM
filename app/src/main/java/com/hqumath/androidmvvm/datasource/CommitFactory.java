package com.hqumath.androidmvvm.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.hqumath.androidmvvm.entity.CommitEntity;
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
public class CommitFactory extends DataSource.Factory<Long, CommitEntity> {

    private MutableLiveData<CommitSource> sourceLiveData = new MutableLiveData<>();
    private WeakReference<LifecycleProvider> lifecycle;

    public CommitFactory(WeakReference<LifecycleProvider> lifecycle) {
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public DataSource<Long, CommitEntity> create() {
        CommitSource commitSource = new CommitSource(lifecycle);
        sourceLiveData.postValue(commitSource);
        return commitSource;
    }

    public MutableLiveData<CommitSource> getSourceLiveData() {
        return sourceLiveData;
    }
}
