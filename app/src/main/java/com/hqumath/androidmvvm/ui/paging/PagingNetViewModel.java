package com.hqumath.androidmvvm.ui.paging;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.datasource.CommitFactory;
import com.hqumath.androidmvvm.datasource.CommitSource;
import com.hqumath.androidmvvm.datasource.UserInfoFactory;
import com.hqumath.androidmvvm.datasource.UserInfoSource;
import com.hqumath.androidmvvm.entity.CommitEntity;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

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

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public LiveData<PagedList<CommitEntity>> list;

    public PagingNetViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    public void init(){
        commitFactory = new CommitFactory(getLifecycleProvider());

        list = new LivePagedListBuilder<>(
                commitFactory,
                new PagedList.Config.Builder()
                        .setPageSize(20)
                        .setEnablePlaceholders(false)//不明确item数目
                        .build())
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

}
