package com.hqumath.androidmvvm.ui.paging;

import android.app.Application;
import androidx.annotation.NonNull;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.datasource.CommitSource;

/**
 * ****************************************************************
 * 文件名称: PagingNetAndDbViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/8/5 14:30
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class PagingNetAndDbViewModel extends BaseViewModel<MyRepository> {

    public PagingNetAndDbViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {

    }

    public void refresh() {

    }

    public void retry() {

    }
}
