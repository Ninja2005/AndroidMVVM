package com.hqumath.androidmvvm.ui.paging;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.entity.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: PagingViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/30 15:17
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class PagingViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PagingViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
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
            for(int i = 0; i < 20; i++){
                UserInfoEntity entity = new UserInfoEntity();
                entity.setLogin("第" + i + "项");
                entity.setAvatar_url("https://www.baidu.com/img/bd_logo1.png");
                list.add(entity);
            }
            model.insertAllUsers(list);
        });
    }
}
