package com.hqumath.androidmvvm.base;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;

/**
 * ****************************************************************
 * 文件名称: BaseViewModel
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 10:14
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class BaseViewModel<M> extends AndroidViewModel {
    protected M model;

    public BaseViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
    }
}
