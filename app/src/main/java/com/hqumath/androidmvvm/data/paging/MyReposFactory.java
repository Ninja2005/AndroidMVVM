package com.hqumath.androidmvvm.data.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.hqumath.androidmvvm.entity.ReposEntity;

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
public class MyReposFactory extends DataSource.Factory<Long, ReposEntity> {

    private MutableLiveData<MyReposSource> sourceLiveData = new MutableLiveData<>();
    private int pageSize;//分页大小

    public MyReposFactory(int pageSize) {
        this.pageSize = pageSize;
    }

    @NonNull
    @Override
    public DataSource<Long, ReposEntity> create() {
        MyReposSource source = new MyReposSource(pageSize);
        sourceLiveData.postValue(source);
        return source;
    }

    public MutableLiveData<MyReposSource> getSourceLiveData() {
        return sourceLiveData;
    }
}
