package com.hqumath.androidmvvm.ui.repos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.data.paging.StarredReposFactory;
import com.hqumath.androidmvvm.data.paging.StarredReposSource;
import com.hqumath.androidmvvm.entity.NetworkState;
import com.hqumath.androidmvvm.entity.ReposEntity;

/**
 * ****************************************************************
 * 文件名称: StarredViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class StarredViewModel extends BaseViewModel<MyRepository> {

    private StarredReposFactory factory;

    public LiveData<PagedList<ReposEntity>> list;
    public LiveData<NetworkState> networkState;//网络状态
    public LiveData<NetworkState> refreshState;//初始化加载状态
    private int pageSize = 10;//每页大小
    private int initialLoadPage = 3;//预加载页数

    public StarredViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        factory = new StarredReposFactory(pageSize);
        networkState = Transformations.switchMap(factory.getSourceLiveData(), source -> source.networkState);
        refreshState = Transformations.switchMap(factory.getSourceLiveData(), source -> source.initialLoad);

        list = new LivePagedListBuilder<>(
                factory,
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setInitialLoadSizeHint(pageSize * initialLoadPage)
                        .setEnablePlaceholders(false)//不明确item数目
                        .build())
                .setFetchExecutor(appExecutors.networkIO())
                .build();
    }

    public void refresh() {
        StarredReposSource source = factory.getSourceLiveData().getValue();
        if (source != null)
            source.invalidate();
    }

    public void retry() {
        StarredReposSource source = factory.getSourceLiveData().getValue();
        if (source != null)
            source.retryAllFailed();
    }
}
