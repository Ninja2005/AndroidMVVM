package com.hqumath.androidmvvm.ui.paging;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.data.paging.CommitFactory;
import com.hqumath.androidmvvm.data.paging.CommitSource;
import com.hqumath.androidmvvm.data.paging.UserInfoFactory;
import com.hqumath.androidmvvm.data.paging.UserInfoSource;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.NetworkState;

/**
 * ****************************************************************
 * 文件名称: PagingDBViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/30 15:17
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class PagingNetViewModel extends BaseViewModel<MyRepository> {

    private CommitFactory commitFactory;

    public LiveData<PagedList<CommitEntity>> list;
    public LiveData<NetworkState> networkState;//网络状态
    public LiveData<NetworkState> refreshState;//初始化加载状态

    public PagingNetViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        commitFactory = new CommitFactory();
        networkState = Transformations.switchMap(commitFactory.getSourceLiveData(), source -> source.networkState);
        refreshState = Transformations.switchMap(commitFactory.getSourceLiveData(), source -> source.initialLoad);

        list = new LivePagedListBuilder<>(
                commitFactory,
                new PagedList.Config.Builder()
                        .setPageSize(20)
                        .setInitialLoadSizeHint(20)
                        .setEnablePlaceholders(false)//不明确item数目
                        .build())
                .setFetchExecutor(appExecutors.networkIO())
                .build();

        /*PagedList.Config config1 = new PagedList.Config.Builder()
                .setPageSize(mPageSize)
                .setPrefetchDistance(mPageSize)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(mPageSize * 3)
                .setMaxSize(Integer.MAX_VALUE)
                .build();*/
    }

    public void refresh() {
        CommitSource commitSource = commitFactory.getSourceLiveData().getValue();
        if(commitSource != null)
            commitSource.invalidate();
    }

    public void retry(){
        CommitSource commitSource = commitFactory.getSourceLiveData().getValue();
        if(commitSource != null)
            commitSource.retryAllFailed();
    }

}
