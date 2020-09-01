package com.hqumath.androidmvvm.ui.about;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;
import com.hqumath.androidmvvm.utils.CommonUtil;

/**
 * ****************************************************************
 * 文件名称: AboutViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/25 11:36
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class AboutViewModel extends BaseViewModel<MyRepository> {

    public MutableLiveData<String> version = new MutableLiveData<>();

    public AboutViewModel(@NonNull Application application) {
        super(application);
        version.setValue(CommonUtil.getVersion());
    }
}
