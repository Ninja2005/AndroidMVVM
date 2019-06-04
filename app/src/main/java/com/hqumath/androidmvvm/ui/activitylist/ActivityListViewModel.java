package com.hqumath.androidmvvm.ui.activitylist;

import android.app.Application;
import androidx.annotation.NonNull;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;

/**
 * ****************************************************************
 * 文件名称: ActivityListViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/4 16:54
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class ActivityListViewModel extends BaseViewModel<MyRepository> {

    public ActivityListViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();

    }
}
