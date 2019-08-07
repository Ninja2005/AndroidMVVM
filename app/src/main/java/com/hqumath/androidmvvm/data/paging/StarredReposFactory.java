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
public class StarredReposFactory extends DataSource.Factory<Long, ReposEntity> {

    private MutableLiveData<StarredReposSource> sourceLiveData = new MutableLiveData<>();
    private int pageSize;//分页大小

    public StarredReposFactory(int pageSize) {
        this.pageSize = pageSize;
    }

    @NonNull
    @Override
    public DataSource<Long, ReposEntity> create() {
        StarredReposSource source = new StarredReposSource(pageSize);
        sourceLiveData.postValue(source);
        return source;
    }

    public MutableLiveData<StarredReposSource> getSourceLiveData() {
        return sourceLiveData;
    }
}
