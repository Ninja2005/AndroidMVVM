package com.hqumath.androidmvvm.ui.paging;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

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

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public final LiveData<PagedList<UserInfoEntity>> list;

    public PagingNetViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
        list = new LivePagedListBuilder<>(
                model.loadAllUsers2(), 50).build();
    }

    public void getData(){
        isLoading.postValue(false);
    }

}
