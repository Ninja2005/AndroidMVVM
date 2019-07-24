package com.hqumath.androidmvvm.ui.myrepos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;

/**
 * ****************************************************************
 * 文件名称: MyReposViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/24 15:41
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyReposViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
//    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    public MyReposViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }
}
