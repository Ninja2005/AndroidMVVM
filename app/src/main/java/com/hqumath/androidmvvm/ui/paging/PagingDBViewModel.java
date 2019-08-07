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
public class PagingDBViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public LiveData<PagedList<UserInfoEntity>> list;

    public PagingDBViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
        list = new LivePagedListBuilder<>(
                model.loadAllUsers2(), 20).build();

        //PagedList.Config config = new PagedList.Config.Builder().setPageSize(50).build();
        /*int mPageSize = 50;
        PagedList.Config config1 = new PagedList.Config.Builder()
                .setPageSize(mPageSize)
                .setPrefetchDistance(mPageSize)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(mPageSize * 3)
                .setMaxSize(Integer.MAX_VALUE)
                .build();*/
    }

    public void getData(){
        isLoading.postValue(false);
    }


    public void delete(){
        appExecutors.diskIO().execute(() -> model.deleteAllUsers());
    }

    public void insert(){
        appExecutors.diskIO().execute(() -> {
            List<UserInfoEntity> list = new ArrayList<>();
            for(int i = 0; i < 1000; i++){
                UserInfoEntity entity = new UserInfoEntity();
                entity.setLogin("第" + i + "项");
                entity.setAvatar_url("https://www.baidu.com/img/bd_logo1.png");
                entity.setIndexInResponse(i);
                list.add(entity);
            }
            model.insertAllUsers(list);
        });
    }
}
