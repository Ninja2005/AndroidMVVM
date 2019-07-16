package com.hqumath.androidmvvm.base;

import androidx.databinding.ViewDataBinding;

/**
 * ****************************************************************
 * 文件名称: BaseViewModelActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/6/3 9:47
 * 文件描述:
 * 注意事项: 需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V> {
    protected VM viewModel;

    /**
     * 初始化ViewModel
     */
    public void initViewModel() {
        viewModel = getViewModel();
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    public abstract VM getViewModel();
}
