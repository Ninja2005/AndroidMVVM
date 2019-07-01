package com.hqumath.androidmvvm.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

/**
 * ****************************************************************
 * 文件名称: CommitFactory
 * 作    者: Created by gyd
 * 创建时间: 2019/6/28 11:56
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class CommitFactory extends DataSource.Factory {
    private MutableLiveData<CommitSource> sourceLiveData = new MutableLiveData<>();

    public CommitFactory(){
    }

    @NonNull
    @Override
    public DataSource create() {
        CommitSource commitSource = new CommitSource();
        sourceLiveData.postValue(commitSource);
        return commitSource;
    }

    public MutableLiveData<CommitSource> getSourceLiveData(){
        return sourceLiveData;
    }
}
