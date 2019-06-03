package com.hqumath.androidmvvm.app;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.hqumath.androidmvvm.data.DemoApiService;
import com.hqumath.androidmvvm.data.DemoRepository;
import com.hqumath.androidmvvm.ui.login.LoginViewModel;

/**
 * ****************************************************************
 * 文件名称: AppViewModelFactory
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:42
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class AppViewModelFactory  extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final DemoRepository mRepository;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, getDemoRepository());
                }
            }
        }
        return INSTANCE;
    }

    private static DemoRepository getDemoRepository() {
        //网络API服务
//        DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);
//        //网络数据源
//        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
//        //本地数据源
//        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
//        //两条分支组成一个数据仓库
//        return DemoRepository.getInstance(httpDataSource, localDataSource);
        return null;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, DemoRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        /*if (modelClass.isAssignableFrom(NetWorkViewModel.class)) {
            return (T) new NetWorkViewModel(mApplication, mRepository);
        } else */if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
