package com.hqumath.androidmvvm.ui.settings;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.hqumath.androidmvvm.base.BaseViewModel;
import com.hqumath.androidmvvm.data.MyRepository;

/**
 * ****************************************************************
 * 文件名称: SettingsViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/7/25 11:18
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class SettingsViewModel extends BaseViewModel<MyRepository> {
    public MutableLiveData<Boolean> isLogout = new MutableLiveData<>();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        model = MyRepository.getInstance();
    }

    /**
     * 退出登录，清空数据
     */
    public void logout() {
        appExecutors.diskIO().execute(() -> {
            //清空数据库
            //清空账户
            model.savePassword("");
            model.saveToken("");
            isLogout.postValue(true);
        });
    }

}
